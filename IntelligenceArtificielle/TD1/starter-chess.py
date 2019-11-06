import time
import chess
import chess.svg
import copy
import random
import os
from time import sleep
from random import randint, choice
from pyinstrument import Profiler

PROFILER = False

piece_val = [{
    'p' : 1,
    'P' : -1,
    'k' : 200,
    'K' : -200,
    'q' : 9,
    'Q' : -9,
    'r' : 5,
    'R' : -5,
    'b' : 3,
    'B' : -3,
    'n' : 3,
    'N' : -3,
},{
    'p' : -1,
    'P' : 1,
    'k' : -200,
    'K' : 200,
    'q' : -9,
    'Q' : 9,
    'r' : -5,
    'R' : 5,
    'b' : -3,
    'B' : 3,
    'n' : -3,
    'N' : 3,
}]

last_refresh = 0
last_refresh_nps = 0
last_nps = 0
current_nps = 0
refresh_interval_ms = 100
refresh_interval_nps_ms = 300
nodes = 0
current_depth = 0
white_h = 0
black_h = 0
current_depth_info = {}

def randomMove(b):
    '''Renvoie un mouvement au hasard sur la liste des mouvements possibles. Pour avoir un choix au hasard, il faut
    construire explicitement tous les mouvements. Or, generate_legal_moves() nous donne un itérateur.'''
    return choice([m for m in b.generate_legal_moves()])

def deroulementRandom(b):
    '''Déroulement d'une partie d'échecs au hasard des coups possibles. Cela va donner presque exclusivement
    des parties très longues et sans gagnant. Cela illustre cependant comment on peut jouer avec la librairie
    très simplement.'''
    print("----------")
    print(b)
    if b.is_game_over():
        print("Resultat : ", b.result())
        return
    b.push(randomMove(b))
    deroulementRandom(b)
    b.pop()

def maxValue(b,alpha,beta, player,depth, max_depth):
    global nodes, current_depth
    nodes += 1
    current_depth = depth
    game_over = b.is_game_over()
    if depth>=max_depth or game_over:
        if game_over:
            if b.is_checkmate():
                return 999 - depth
            return 0
        else:
            return getBoardScore(b,player)
            
    for m in b.generate_legal_moves():
        b.push(m)
        val = minValue(b,alpha,beta,player,depth+1,max_depth)
        b.pop()
        if(alpha < val):
            alpha = val
        if(alpha >= beta):
            return beta
    return alpha
            
def minValue(b,alpha,beta, player, depth, max_depth):
    global nodes, current_depth
    nodes += 1
    current_depth = depth
    game_over = b.is_game_over()
    if depth>=max_depth or game_over:
        if game_over:
            if b.is_checkmate():
                return -999
            return 0
        else:
            return getBoardScore(b,player)
            
    for m in b.generate_legal_moves():
        b.push(m)
        val = maxValue(b,alpha,beta,player,depth+1,max_depth)

        b.pop()
        if(beta > val):
            beta = val
        if(alpha >= beta):
            return alpha
    return beta

def NegAlphaBeta(b, alpha, beta, player, depth, max_depth):
    global nodes, current_depth
    game_over = b.is_game_over()

    next_player = -player
    nodes += 1
    
    if depth>=max_depth or game_over:
        if game_over:
            if b.is_checkmate():
                return 999 * player
            return 0
        else:
            return getBoardScore(b,0 if player == -1 else 1)

    current_depth = depth
    refresh()

    for m in b.generate_legal_moves():
        b.push(m)
        val = -NegAlphaBeta(b, alpha, beta, next_player, depth+1, max_depth)
        b.pop()

        if val>alpha:
            alpha = val
            if alpha>beta:
                return alpha
    
    return alpha

def NegAlphaBetaCredit(b, alpha, beta, the_player, player, credit, current_val, depth):
    global nodes, current_depth
    legal_moves = list(b.generate_legal_moves())
    game_over = b.is_game_over()

    next_player = -player
    nodes += 1
    current_depth = depth
    val = getBoardScorePreload(b,legal_moves,0 if player == -1 else 1)
    
    if credit<0 or game_over:
        if game_over:
            if b.is_checkmate():
                return 999 * player
            return 0
        else:
            return val

    diff = current_val - val
    if diff <0.5:
        # Uninteresting move
        credit -= 35
    elif diff>=2:
        credit -= 5
    else:
        credit -= 10


    legal_moves = list(b.generate_legal_moves())

    current_depth_info[depth+1] = [0,len(legal_moves)]

    refresh()

    for m in legal_moves:
        current_depth_info[depth+1][0] += 1
        b.push(m)
        val = -NegAlphaBetaCredit(b, -alpha, -beta, not the_player, next_player, credit, val, depth+1)
        b.pop()

        if val>alpha:
            alpha = val
            if alpha>beta:
                return alpha

    del(current_depth_info[depth+1])
    
    return alpha

def getBoardScore(b,player):
    return getBoardScorePreload(b,b.generate_legal_moves(),player)

def getBoardScorePreload(b,legal_moves,player):
    score = 0
    moves_available = 0
    for k,p in b.piece_map().items():
        score += piece_val[player][p.symbol()]

    for m in legal_moves:
        moves_available += 1

        p = b.piece_at(m.to_square)
        if p:
            score += - piece_val[player][p.symbol()] / 2

    score += moves_available / 20
    return score

def nextMove_AI(b,player,depth):
    global white_h, black_h
    best = -999
    scores = {}
    for m in b.generate_legal_moves():
        b.push(m)
        score = minValue(b,-1000,1000,1,0,depth)
        b.pop()

        if(score > best):
            best = score

        if(player == 1):
            white_h = best
        else:
            black_h = best

        refresh()

        if str(score) in scores:
            scores[str(score)].append(m)
        else:
            scores[str(score)] = [m]
    print(("White" if player == 1 else "Black") +": "+str(best))
    return random.choice(scores[str(best)])

def nextMove_NegAlphaBeta(b,player,depth): 
    global white_h, black_h, current_depth_info
    best = -999
    scores = {}

    legal_moves = list(b.generate_legal_moves())

    current_depth_info[depth] = [0,len(legal_moves)]

    for m in legal_moves:
        current_depth_info[depth][0] += 1
        b.push(m)
        score = -NegAlphaBeta(b,-1000,1000,-1 if player == 0 else 1,0,depth)
        b.pop()

        if(score > best):
            best = score

        if(player == 1):
            white_h = best
        else:
            black_h = best

        if str(score) in scores:
            scores[str(score)].append(m)
        else:
            scores[str(score)] = [m]
    print("White" if player == 1 else "Black" +": "+str(best))
    return random.choice(scores[str(best)])

def nextMove_NegAlphaBetaCredit(b,player,depth): 
    global white_h, black_h, current_depth_info
    best = -999
    credit = depth * 10
    scores = {}
    current_depth_info = {}

    legal_moves = list(b.generate_legal_moves())

    current_depth_info[0] = [0,len(legal_moves)]

    for m in legal_moves:
        current_depth_info[0][0] += 1
        b.push(m)
        score = -NegAlphaBetaCredit(b,-1000,1000,False,-1 if player == 0 else 1,credit,0,0)
        b.pop()

        if(score > best):
            best = score

        if(player == 1):
            white_h = best
        else:
            black_h = best

        if str(score) in scores:
            scores[str(score)].append(m)
        else:
            scores[str(score)] = [m]
    print("White" if player == 1 else "Black" +": "+str(best))
    return random.choice(scores[str(best)])

def nextMove_AI_3(b,player):  
    return nextMove_AI(b,player,5)

def nextMove_AI_2(b,player):  
    return nextMove_AI(b,player,3)

def nextMove_AI_1(b,player):  
    return nextMove_AI(b,player,1)

def nextMove_NegAlphaBeta_3(b,player):  
    return nextMove_NegAlphaBeta(b,player,5)

def nextMove_NegAlphaBeta_2(b,player):  
    return nextMove_NegAlphaBeta(b,player,3)

def nextMove_NegAlphaBeta_1(b,player):  
    return nextMove_NegAlphaBeta(b,player,1)

def nextMove_NegAlphaBetaCredit_2(b,player):  
    return nextMove_NegAlphaBetaCredit(b,player,4)

def nextMove_NegAlphaBetaCredit_1(b,player):  
    return nextMove_NegAlphaBetaCredit(b,player,3)

def nextMove_Random(b,c):    
    return randomMove(b)

def nextMove_Human(b,c):
    print("-------- Legal Moves --------")
    moves = {}
    for m in b.generate_legal_moves():
        moves[str(m)] = m

    i = 0
    for m in moves:
        print(str(i)+": "+str(m))
        i += 1

    r = None
    while(r == None):
        try:
            r = moves[input("selection: ")]
        except:
            print("Invalid move")

    return r

def parcoursProfondeur(b,depth, max_depth):
    global nodes, white_h, black_h, current_depth
    if depth >= max_depth:
        return

    current_depth = depth
    white_h = getBoardScore(b,1)
    black_h = getBoardScore(b,0)
    
    for m in b.generate_legal_moves():
        b.push(m)
        nodes += 1
        parcoursProfondeur(b,depth+1,max_depth)
        b.pop()

    refresh()

def parcoursAll(b,k):
    for n in range(1,k+1):
        parcoursProfondeur(b,0,n)

def genericGame(b,white_def,black_def, silent = False):
    movelimit = 0
    current_move = 0
    c = 1
    while((current_move <= movelimit or movelimit <= 0) and not b.is_game_over()):
        current_move += 1
        if not silent: 
            sleep(0.2)
            f = open("current.svg","w")
            f.write(chess.svg.board(board=b,size=400))
            f.close()
        bs = copy.deepcopy(b)
        if(c == 1):
            b.push(white_def(bs,1))
        else:
            b.push(black_def(bs,0))
        c = -c
    
    if not silent: 
        print("Resultat : ", b.result())
        sleep(0.2)
        f = open("current.svg","w")
        f.write(chess.svg.board(board=b,size=400))
        f.close()
    return

def refresh():
    global nodes, last_refresh, refresh_interval_ms, last_nps, last_refresh_nps, refresh_interval_nps_ms, current_depth, current_nps, current_depth_info
    millis = int(round(time.time() * 1000))

    if(millis - refresh_interval_nps_ms >= last_refresh_nps):
        current_nps = int((nodes - last_nps) * (1000/refresh_interval_nps_ms))
        last_nps = nodes
        last_refresh_nps = millis

    if(millis - refresh_interval_ms >= last_refresh):
        os.system('clear')
        print("Nodes: "+str(nodes)+" ("+str(current_nps)+" /s)")

        for d,v in current_depth_info.items():
            print("D"+str(d)+": "+str(v[0])+"/"+str(v[1]))

        print("Depth: "+str(current_depth))
        print("White: "+str(white_h))
        print("Black: "+str(black_h))
        last_refresh = millis

board = chess.Board()

# parcoursAll(board,10)

if PROFILER:
    profiler = Profiler()
    profiler.start()

genericGame(board,nextMove_NegAlphaBetaCredit_2,nextMove_NegAlphaBetaCredit_2)

if PROFILER:
    profiler.stop()

    print(profiler.output_text(unicode=True, color=True))