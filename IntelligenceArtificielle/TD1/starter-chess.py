import time
import chess
import chess.svg
import copy
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
                return 0,0
            if b.is_checkmate():
                return 999,0
        else:
            return getBoardScore(b,player),0
    best = 0
    for m in b.generate_legal_moves():
        b.push(m)
        val,move = minValue(b,alpha,beta,player,depth+1,max_depth)
        b.pop()
        if(alpha < val):
            alpha = val
            best = m
        if(alpha >= beta):
            return beta, m
    return alpha,best
            
def minValue(b,alpha,beta, player, depth, max_depth):
    if depth>=max_depth:
        if b.is_game_over():
            if b.is_stalemate():
                return 0,0
            if b.is_checkmate():
                return -999,0
        else:
            return getBoardScore(b,player),0
    best = 0
    for m in b.generate_legal_moves():
        b.push(m)
        val,move = maxValue(b,alpha,beta,player,depth+1,max_depth)

        b.pop()
        if(beta > val):
            beta = val
            best = m
        if(alpha >= beta):
            return alpha,m
    return beta,best

def getBoardScore(b,player):
    score = 0
    for k,p in b.piece_map().items():
        score += piece_val[player][p.symbol()]
    return score

def nextMove_AI_3(b,player):
    depth = 5
    
    score,move = maxValue(b,-999,999,player,0,depth)
    print(str(player)+": "+str(score))
    return move

def nextMove_Random(b,c):    
    return randomMove(b)

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
            sleep(0.5)
            f = open("current.svg","w")
            f.write(chess.svg.board(board=b))
            f.close()
        bs = copy.deepcopy(b)
        if(c == 1):
            b.push(white_def(bs,1))
        else:
            b.push(black_def(bs,0))
        c = -c
    
    if not silent: 
        print("Resultat : ", b.result())
    return

board = chess.Board()

# parcoursAll(board,10)

genericGame(board,nextMove_AI_3,nextMove_Random)