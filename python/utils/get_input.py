import requests


def get_input(year, day):
    cookies = {'session': 'your_session_id'}
    return requests.get(f'https://adventofcode.com/{year}/day/{day}/input', cookies=cookies).text
