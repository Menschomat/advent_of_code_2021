import sys
import utils


def get_input():
    return [int(i_str) for i_str in utils.get_input(2021, 1).strip().split("\n")]


def part_one(inp_list):
    return len([x for i, x in enumerate(inp_list) if i > 0 and x > inp_list[i - 1]])


def part_two(inp_list):
    last_win = sys.maxsize
    i = 0
    c = 0
    while i < len(inp_list) - 2:
        win = sum([inp_list[i], inp_list[i + 1], inp_list[i + 2]])
        if win > last_win:
            c += 1
        last_win = win
        i += 1
    return c
