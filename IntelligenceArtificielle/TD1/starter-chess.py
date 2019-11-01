import time
import chess
import chess.svg
import copy
import random
from time import sleep
from random import randint, choice

piece_val = [{
    'p' : 1,
    'P' : -1,
    'k' : 0,
    'K' : 0,
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
    'k' : 0,
    'K' : 0,
    'q' : -9,
    'Q' : 9,
    'r' : -5,
    'R' : 5,
    'b' : -3,
    'B' : 3,
    'n' : -3,
    'N' : 3,
}]

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
    if depth>=max_depth or b.is_game_over():
        if b.is_game_over():
            if b.is_stalemate():
                return 0
            if b.is_checkmate():
                return 999
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
    if depth>=max_depth:
        if b.is_game_over():
            if b.is_stalemate():
                return 0
            if b.is_checkmate():
                return -999
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

def getBoardScore(b,player):
    score = 0
    for k,p in b.piece_map().items():
        score += piece_val[player][p.symbol()]
    return score

def nextMove_AI(b,player,depth):    
    best = -999
    scores = {}
    for m in b.generate_legal_moves():
        b.push(m)
        score = minValue(b,-999,999,player,0,depth)
        b.pop()

        if(score > best):
            best = score

        if str(score) in scores:
            scores[str(score)].append(m)
        else:
            scores[str(score)] = [m]
    
    return random.choice(scores[str(best)])

def nextMove_AI_3(b,player):  
    return nextMove_AI(b,player,5)

def nextMove_AI_2(b,player):  
    return nextMove_AI(b,player,3)

def nextMove_AI_1(b,player):  
    return nextMove_AI(b,player,1)

def nextMove_Random(b,c):    
    return randomMove(b)

def nextMove_Human(b,c):
    print("--------")
    moves = []
    for m in b.generate_legal_moves():
        moves.append(m)

    i = 0
    for m in moves:
        print(str(i)+": "+str(m))
        i += 1
    return moves[int(input("selection: "))]

def parcoursProfondeur(b,depth, max_depth,nodes):
    if depth >= max_depth:
        return nodes
    
    for m in b.generate_legal_moves():
        b.push(m)
        nodes += 1
        nodes = parcoursProfondeur(b,depth+1,max_depth,nodes)
        b.pop()

    return nodes

def parcoursAll(b,k):
    for n in range(1,k+1):
        nodes = parcoursProfondeur(b,0,n,0)
        print("Depth "+str(n)+" (nodes: "+str(nodes)+")")

def genericGame(b,white_def,black_def, silent = False):
    c = 1
    while(not b.is_game_over()):
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

board = chess.Board()

# parcoursAll(board,10)

genericGame(board,nextMove_AI_2,nextMove_AI_2)