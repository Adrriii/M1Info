# -*- coding: utf-8 -*-

import time
import copy
import Tictactoe 
from random import randint,choice

import os

class bcolors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'

noeuds = 0

#running robin's code
VICTORY  =  1
DEFEAT   = -1
EQUALITY =  0

def nextMove_Robin_rec(b):
    if (b.is_game_over()):
        result = getresult(b)

        if (result != DEFEAT):
            return [True, result]

        return [False, DEFEAT]

    ex = []

    for i in b.legal_moves():
        b.push(i)
        result = nextMove_Robin_rec(b)
        b.pop()

        if result[0]:
            if result[1] == VICTORY:
                return result
                
            if len(ex) == 0:
                ex = result

    return [False, []] if len(ex) == 0 else ex


def nextMove_Robin(b,c):
    '''
    Return the best possible next move for X
    '''

    ex = []

    for i in b.legal_moves():
        b.push(i)
        result = nextMove_Robin_rec(b)

        if (result[0]):
            if (result[1] == VICTORY):
                b.pop()
                return i

            if len(ex) == 0:
                ex = i
        b.pop()

    if (len(ex) == 0):
        return RandomMove(b)
    return ex

#####

def RandomMove(b):
    '''Renvoie un mouvement au hasard sur la liste des mouvements possibles'''
    return choice(b.legal_moves())

def deroulementRandom(b):
    '''Effectue un déroulement aléatoire du jeu de morpion.'''
    print("----------")
    print(b)
    if b.is_game_over():
        res = getresult(b)
        if res == 1:
            print("Victoire de X")
        elif res == -1:
            print("Victoire de O")
        else:
            print("Egalité")
        return
    b.push(RandomMove(b))
    deroulementRandom(b)
    b.pop()


def getresult(b):
    '''Fonction qui évalue la victoire (ou non) en tant que X. Renvoie 1 pour victoire, 0 pour 
       égalité et -1 pour défaite. '''
    if b.result() == b._X:
        return 1
    elif b.result() == b._O:
        return -1
    else:
        return 0

def possibleGames(b,c):
    '''Trouve le nombre possible de parties.'''
    global noeuds
    
    if b.is_game_over():
        # print("Game: "+str(c))
        return c+1
    for m in b.legal_moves():
        b.push(m)
        noeuds = noeuds + 1
        c = possibleGames(b,c)
        b.pop()
    return c
            
def maxValue(b,alpha,beta, c):
    if b.is_game_over():
        return getresult(b) * c
    for m in b.legal_moves():
        b.push(m)
        val = minValue(b,alpha,beta,c)
        b.pop()
        if(alpha < val):
            alpha = val
        if(alpha >= beta):
            return beta
    return alpha
            
def minValue(b,alpha,beta, c):
    if b.is_game_over():
        return getresult(b) * c
    for m in b.legal_moves():
        b.push(m)
        val = maxValue(b,alpha,beta,c)
        b.pop()
        if(beta > val):
            beta = val
        if(alpha >= beta):
            return alpha
    return beta

def nextMove_Random(b,c):    
    return RandomMove(b)

def nextMove_AI(b,c):
    maxval = -2
    bestmove = 0

    for m in b.legal_moves():
        b.push(m)
        val = minValue(b,-2,2,c)
        b.pop()

        if(val > maxval):
            maxval = val
            bestmove = m
    
    return bestmove

def nextMove_Human(b,h):
    move = []
    if h == 1:
        move.append('X')
    else:
        move.append('O')
    in_move = int(input("Next move: "))

    if in_move <= 3:
        move.append(2)
    elif in_move <= 6:
        move.append(1)
    else:
        move.append(0)

    if in_move == 7 or in_move == 4 or in_move == 1:
        move.append(0)
    elif in_move == 8 or in_move == 5 or in_move == 2:
        move.append(1)
    else:
        move.append(2)

    return move

def genericGame(b,X_def,O_def, silent = False):
    c = 1
    while(not b.is_game_over()):
        if not silent: 
            print(b)
        bs = copy.deepcopy(b)
        if(c == 1):
            b.push(X_def(bs,1))
        else:
            b.push(O_def(bs,-1))
        c = -c
    
    if not silent: 
        print(b)
    return getresult(b)

def play(b, X_def, O_def, number):
    victory = 0
    p = 0
    for i in range(1, number + 1):
        p+=1
        r = genericGame(Tictactoe.Board(), X_def, O_def, False)
        victory += r

        if (p % 1 == 0 or i == number):
            os.system("clear")
            print(17 * '*')
            if (r == 1):
                print("%.2f" % (i/number * 100) + " % -> " + bcolors.OKGREEN + "%.2f" % (victory/i * 100) + bcolors.ENDC)

            elif (r == 0):
                print("%.2f" % (i/number * 100) + " % -> " + bcolors.OKBLUE + "%.2f" % (victory/i * 100) + bcolors.ENDC)
            
            else:
                print("%.2f" % (i/number * 100) + " % -> " + bcolors.FAIL + "%.2f" % (victory/i * 100) + bcolors.ENDC)
            print(17 * '*')

board = Tictactoe.Board()

### Deroulement d'une partie aléatoire
#deroulementRandom(board)

### Nombre de parties
# print("Possible games: "+str(possibleGames(board,0)))
# print("Noeuds : "+str(noeuds))

#playVsHuman(board,-1)

play(board,nextMove_Human,nextMove_AI, 1000)