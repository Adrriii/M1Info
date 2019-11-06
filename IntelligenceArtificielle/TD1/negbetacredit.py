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
refresh_interval_ms = 50
refresh_interval_nps_ms = 300
thinking_start = 0
credit_run_out_time = 5000
nodes = 0
current_depth = 0
white_h = 0
black_h = 0
current_depth_info = {}

def now():
    return int(round(time.time() * 1000))

def getBoardScore(b,player):
    score = 0
    moves_available = 0
    for k,p in b.piece_map().items():
        score += piece_val[player][p.symbol()]

    for m in b.generate_legal_moves():
        moves_available += 1

        p = b.piece_at(m.to_square)
        if p:
            score += - piece_val[player][p.symbol()] / 2

    score += moves_available / 40
    return score

def NegAlphaBetaCredit(b, alpha, beta, player, credit, current_val, val, depth):
    global nodes, current_depth, thinking_start, credit_run_out_time
    game_over = b.is_game_over()

    next_player = -player
    nodes += 1
    current_depth = depth

    if(now() - thinking_start >= credit_run_out_time):
        credit -= 10
    
    if credit<0 or game_over:
        if game_over:
            if b.is_checkmate():
                return 999 * player
            return 0
        else:
            return val

    diff = -current_val - val
    if diff <0.5:
        # Uninteresting move
        credit -= 35
    elif diff>=2:
        credit -= 3
    else:
        credit -= 10

    current_depth_info[depth+1] = [0,0]

    refresh()

    ms = {}
    for m in b.generate_legal_moves():
        b.push(m)
        val = getBoardScore(b,0 if player == -1 else 1)
        ms[val] = m
        b.pop()

    for m in sorted(ms):
        current_depth_info[depth+1][0] += 1
        b.push(ms[m])
        val = -NegAlphaBetaCredit(b, -beta, -alpha, next_player, credit, val, m, depth+1)
        b.pop()

        if val>alpha:
            alpha = val
            if alpha>beta:
                del(current_depth_info[depth+1])
                return alpha

    del(current_depth_info[depth+1])
    
    return alpha

def nextMove_NegAlphaBetaCredit(b,player,depth): 
    global white_h, black_h, current_depth_info, thinking_start
    best = -999
    credit = depth * 10
    scores = {}
    current_depth_info = {}
    thinking_start = now()

    curr = getBoardScore(b,0 if player == -1 else 1)

    ms = {}
    for m in b.generate_legal_moves():
        b.push(m)
        val = getBoardScore(b,0 if player == -1 else 1)
        ms[val] = m
        b.pop()

    current_depth_info[0] = [0,len(ms)]

    for m in sorted(ms):
        current_depth_info[0][0] += 1
        b.push(ms[m])
        score = -NegAlphaBetaCredit(b,-1000,1000,-1 if player == 0 else 1,credit,curr,m,0)
        b.pop()

        if(score > best):
            best = score

        if(player == 1):
            white_h = best
        else:
            black_h = best

        if str(score) in scores:
            scores[str(score)].append(ms[m])
        else:
            scores[str(score)] = [ms[m]]
            
    return random.choice(scores[str(best)])

def nextMove_NegAlphaBetaCredit_2(b,player):  
    return nextMove_NegAlphaBetaCredit(b,player,5)

def nextMove_NegAlphaBetaCredit_1(b,player):  
    return nextMove_NegAlphaBetaCredit(b,player,3)

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

def genericGame(b,white_def,black_def, silent = False):
    movelimit = 0
    current_move = 0
    c = 1
    while((current_move <= movelimit or movelimit <= 0) and not b.is_game_over()):
        current_move += 1
        if not silent: 
            sleep(0.1)
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
        sleep(0.1)
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

        for d in sorted(current_depth_info.keys()):
            print("D"+str(d)+": "+str(current_depth_info[d][0])+("/"+str(current_depth_info[d][1]) if (current_depth_info[d][1] != 0)  else ""))

        print("Depth: "+str(current_depth))
        print("White: "+str(white_h))
        print("Black: "+str(black_h))
        last_refresh = millis

board = chess.Board()

if PROFILER:
    profiler = Profiler()
    profiler.start()

genericGame(board,nextMove_NegAlphaBetaCredit_2,nextMove_NegAlphaBetaCredit_2)

if PROFILER:
    profiler.stop()

    print(profiler.output_text(unicode=True, color=True))