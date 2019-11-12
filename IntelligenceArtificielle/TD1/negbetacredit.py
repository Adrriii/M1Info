import time
import chess
import chess.svg
import chess.pgn
import copy
import random
import os
from time import sleep
from random import randint, choice
from pyinstrument import Profiler

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
refresh_interval_svg = 200
last_refresh_svg = 0
thinking_start = 0
credit_run_out_time = 100000
nodes = 0
current_depth = 0
last_move = 0
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

        #p = b.piece_at(m.to_square)
        #if p:
        #    score += - piece_val[player][p.symbol()] / 2

    score += moves_available / 20
    return score

def NegAlphaBetaCredit(b, arrows, alpha, beta, player, credit, current_val, val, depth):
    global nodes, current_depth, thinking_start, credit_run_out_time
    game_over = b.is_game_over()

    next_player = -player
    nodes += 1
    current_depth = depth

    spent = now() - thinking_start
    if(spent >= credit_run_out_time /10):
        remove = int((spent / credit_run_out_time) * 10)
        credit -= remove
    
    if credit<0 or game_over:
        if game_over:
            if b.is_checkmate():
                return 999
            return 0
        else:
            return val

    diff = -current_val - val
    if diff <0.5:
        # Uninteresting move
        credit -= 35
    elif diff>=2:
        credit -= 5
    elif diff>=5:
        credit -= 2
    else:
        credit -= 10
    current_depth_info[depth+1] = [0,0]

    refresh(arrows=arrows)

    ms = []
    for m in b.generate_legal_moves():
        b.push(m)
        val = getBoardScore(b,0 if player == -1 else 1)
        ms.append((val,m))
        b.pop()

    for m in sorted(ms, key=lambda x: x[0]):
        mv = m[1]
        current_depth_info[depth+1][0] += 1
        arrows.append(chess.svg.Arrow(mv.from_square,mv.to_square,color=("#f00" if player == 1 else "#00f")))
        b.push(mv)
        val = -NegAlphaBetaCredit(b, arrows, -beta, -alpha, next_player, credit, val, m[0], depth+1)
        arrows.pop()
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
    arrows = []
    thinking_start = now()

    curr = getBoardScore(b,0 if player == -1 else 1)

    ms = []
    for m in b.generate_legal_moves():
        b.push(m)
        val = getBoardScore(b,0 if player == -1 else 1)
        ms.append((val,m))
        b.pop()

    current_depth_info[0] = [0,len(ms)]

    for m in sorted(ms, key=lambda x: x[0]):
        mv = m[1]
        current_depth_info[0][0] += 1
        arrows.append(chess.svg.Arrow(mv.from_square,mv.to_square,color="#0f0"))
        b.push(mv)
        score = -NegAlphaBetaCredit(b, arrows,-1000,1000,-1 if player == 0 else 1,credit,curr,m[0],0)
        arrows.pop()
        b.pop()

        if(score > best):
            best = score

        if(player == 1):
            white_h = best
        else:
            black_h = best

        if str(score) in scores:
            scores[str(score)].append(mv)
        else:
            scores[str(score)] = [mv]
            
    return random.choice(scores[str(best)])

def nextMove_NegAlphaBetaCredit_2(b,player):  
    return nextMove_NegAlphaBetaCredit(b,player,5)

def nextMove_NegAlphaBetaCredit_1(b,player):  
    return nextMove_NegAlphaBetaCredit(b,player,3)

def nextMove_Human(b,c):
    global last_refresh_svg
    last_refresh_svg = 0
    refresh(board=b,arrows=[])
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
    global last_move
    movelimit = 0
    current_move = 0
    c = 1
    while((current_move <= movelimit or movelimit <= 0) and not b.is_game_over()):
        current_move += 1
        refresh(b)
        if not silent: 
            sleep(0.1)
        bs = copy.deepcopy(b)
        if(c == 1):
            last_move = white_def(bs,1)
        else:
            last_move = black_def(bs,0)
        b.push(last_move)
        c = -c
    
    if not silent: 
        print("Resultat : ", b.result())
        refresh(b)
    return

def refresh(board = None, arrows = None):
    global nodes, last_move, last_board, last_refresh, refresh_interval_ms, last_nps, last_refresh_nps, refresh_interval_nps_ms, refresh_interval_svg, last_refresh_svg, current_depth, current_nps, current_depth_info
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

    if board == None:
        board = last_board

    if millis - refresh_interval_svg >= last_refresh_svg:
        f = open("current.svg","w")
        if arrows != None:
            f.write(chess.svg.board(board=board,arrows=arrows,size=400,lastmove=last_move))
        else:
            f.write(chess.svg.board(board=board,size=400,lastmove=last_move))
        f.close()
        last_refresh_svg = millis


PROFILER = False
START_FROM_PGN = True
PGN_FILE = "kasparov-deepBlue-1997-4.pgn"

if START_FROM_PGN:
    pgn = open(PGN_FILE)  
    game = chess.pgn.read_game(pgn)
    game.headers
    board = game.board()
        
    moves = [move for move in game.mainline_moves()]
    for m in moves[:-1]:
        board.push(m)
else:
    board = chess.Board()

last_board = board

if PROFILER:
    profiler = Profiler()
    profiler.start()

genericGame(board,nextMove_NegAlphaBetaCredit_1,nextMove_NegAlphaBetaCredit_2)

if PROFILER:
    profiler.stop()

    print(profiler.output_text(unicode=True, color=True))