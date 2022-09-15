#
# ps6pr5.py (Problem Set 6, Problem 5)
#
# TT Securities
#
# Computer Science 111
#

import math


def display_menu():
    """ prints a menu of options
        """
    print()
    print('(0) Input a new list of prices')
    print('(1) Print the current prices')
    print('(2) Find the latest price')
    print('(3) Find the average price')
    print('(4) Find the standard deviation')
    print('(5) Find the max price and its day')
    print('(6) Test a threshold')
    print('(7) Your investment plan')
    print('(8) Quit')
    print()





def get_new_prices():
    """ gets a new list of prices from the user and returns it
        """
    new_price_list = eval(input('Enter a new list of prices: '))
    return new_price_list


def print_prices(prices):
    """ prints the current list of prices
            input: prices is a list of 1 or more numbers.
        """
    if len(prices) == 0:
        print('No prices have been entered.')
        return

    print('Day Price')
    print('--- -----')
    for i in range(len(prices)):
        print('%3d' % i, end='')
        print('%6.2f' % prices[i])


def latest_price(prices):
    """ returns the latest (i.e., last) price in a list of prices.
            input: prices is a list of 1 or more numbers.
        """
    return prices[-1]

def avg_price(list):
    """ returns the average of the values inside list
            input list: any list of 1 more elements consisted of positive integers
        """
    sum = 0
    for x in list:
        sum += x
    return sum / len(list)


def std_dev(list):
    """ returns the standard deviation of prices
            input list: any list of 1 more elements consisted of positive integers
        """
    sd = 0
    for x in list:
        sd_help = (x - avg_price(list)) ** 2
        sd += sd_help
    sd = sd / len(list)
    return sd ** (1 / 2)


def max_day(list):
    """ returns the index of the maximum value in the list (parameter variable)
            input list: any list of 1 more elements consisted of positive integers
        """
    current_max = list[0]
    index = 0
    for i in range(1, len(list)):
        if current_max < list[i]:
            current_max = list[i]
            index = i
    return index


def any_below(list, threshold):
    """ returns whether there are any prices below the threshold (True or False)
            input list: any list of 1 more elements consisted of positive integers
            input threshold: any positive integer
        """
    for x in list:
        if threshold > x:
            return True
    return False


def find_plan(list):
    """ returns the buy day and sell day and the profit in a list
            input list: any list of 2 or more elements consisted of positive integers
        """
    profit = []
    i_list = []
    y_list = []
    for i in range(len(list)):
        for y in range(i + 1, len(list)):
            if list[y] > list[i]:
                profit += [list[y] - list[i]]
                i_list += [i]
                y_list += [y]
    ma_day = max_day(profit)
    max_profit = profit[ma_day]
    buy_day = i_list[ma_day]
    sell_day = y_list[ma_day]
    return [buy_day] + [sell_day] + [max_profit]

def tts():
    """ the main user-interaction loop
        """
    prices = []

    while True:
        display_menu()
        choice = int(input('Enter your choice: '))
        print()

        if choice == 0:
            prices = get_new_prices()
        elif choice == 8:
            break
        elif choice < 8 and len(prices) == 0:
            print('You must enter some prices first.')
        elif choice == 1:
            print_prices(prices)
        elif choice == 2:
            latest = latest_price(prices)
            print('The latest price is', latest)
        elif choice == 3:
            print('The average price is', avg_price(prices))
        elif choice == 4:
            print('The standard deviation is', std_dev(prices))
        elif choice == 5:
            print('The max price is', prices[max_day(prices)], "on day", max_day(prices))
        elif choice == 6:
            threshold = int(input('Enter your threshold'))
            if any_below(prices, threshold) == True:
                    print('There is at least one price below', threshold)
            else:
                    print('There are no prices below', threshold)
        elif choice == 7:
            print('Buy on day', find_plan(prices)[0], 'at price', prices[find_plan(prices)[0]])
            print('Sell on day', find_plan(prices)[1], 'at price', prices[find_plan(prices)[1]])
            print('Total profit:', find_plan(prices)[2])


        else:
            print('Invalid choice. Please try again.')

    print('See you yesterday!')
