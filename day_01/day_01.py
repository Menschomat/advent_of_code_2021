import requests
import sys
from utils import get_input

in_list = [int(i_str) for i_str in get_input(2021,1).strip().split("\n")]

def part_one(input):
  return len([x for i,x in enumerate(input) if i > 0 and x > input[i-1] ])

def part_two(input):
  last_win = sys.maxsize
  i = 0
  c = 0
  while i < len(input)-2:
    win = sum([input[i],input[i+1],input[i+2]])
    if win > last_win:
      c+=1
    last_win = win
    i+= 1
  return c

print("Part 1: ", part_one(in_list))
print("Part 2: ", part_two(in_list))