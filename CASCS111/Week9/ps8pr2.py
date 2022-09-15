#
# ps8pr2.py  (Problem Set 8, Problem 2)
#
# A class to represent calendar dates
#

class Date:
    """ A class that stores and manipulates dates that are
        represented by a day, month, and year.
    """

    # The constructor for the Date class.
    def __init__(self, init_month, init_day, init_year):
        """ constructor that initializes the three attributes
            in every Date object (month, day, and year)
        """
        self.month = init_month
        self.day = init_day
        self.year = init_year
        # add the necessary assignment statements below


    # The function for the Date class that returns a Date
    # object in a string representation.
    def __repr__(self):
        """ This method returns a string representation for the
            object of type Date that it is called on (named self).

            ** Note that this _can_ be called explicitly, but
              it more often is used implicitly via printing or evaluating.
        """
        s = '%02d/%02d/%04d' % (self.month, self.day, self.year)
        return s

    def is_leap_year(self):
        """ Returns True if the called object is
            in a leap year. Otherwise, returns False.
        """
        if self.year % 400 == 0:
            return True
        elif self.year % 100 == 0:
            return False
        elif self.year % 4 == 0:
            return True
        return False

    def copy(self):
        """ Returns a new object with the same month, day, year
            as the called object (self).
        """
        new_date = Date(self.month, self.day, self.year)
        return new_date

    def advance_one(self):
        """ changes the called object so that it represents one
            calendar day after the date that it originally represented
        """
        days_in_month = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
        self.day += 1
        the_month = days_in_month[self.month]
        if self.day > the_month:
            if self.month == 2 and self.is_leap_year()== True and self.day == 29:
                self.day += 0
            else:
                self.day = 1
                self.month += 1
                if self.month == 13:
                    self.month = 1
                    self.year += 1


    def advance_n(self, n):
        """ changes the calling object so that it represents n calendar
            days after the date it originally represented and prints
            all of the dates from the starting date to the finishing date,
            inclusive of both endpoints.
            input n: any positive integer or 0
        """
        print(self)
        for x in range(n):
            self.advance_one()
            print(self)

    def __eq__(self, other):
        """ returns True if the called object (self) and the argument (other)
            represent the same calendar date or False otherwise
            input other: other Date
        """
        if (self.year == other.year and self.month == other.month and self.day == other.day):
            return True
        else:
            return False

    def is_before(self,other):
        """ returns True if the called object represents a calendar date
            that occurs before the calendar date that is represented by other or
            returns False if self and other represent the same day, or if
            self occurs after other
            input other: other Date
        """
        if self.year < other.year:
            return True
        elif self.year == other.year:
            if self.month < other.month:
                return True
            elif self.month == other.month:
                if self.day < other.day:
                    return True
                else:
                    return False
            else:
                return False
        else:
            return False


    def is_after(self,other):
        """ returns True if the calling object represents a calendar date
            that occurs after the calendar date that is represented by other or
            returns False if self and other represent the same day, or if
            self occurs before other
            input other: other Date
        """
        if self == other:
            return False
        answer = self.is_before(other)
        if answer == True:
            return False
        else:
            return True

    def days_between(self, other):
        """ returns an integer that represents the number of
            days between self and other
            input other: other Date
        """
        one_day = self.copy()
        two_day = other.copy()
        value = 0
        if one_day == two_day:
            return value
        elif one_day.is_before(two_day) == True:
            while one_day.is_before(two_day) == True:

                one_day.advance_one()
                value -= 1
        else:
            while one_day.is_before(two_day) == False:
                two_day.advance_one()
                value += 1
            value -= 1
        return value

    def day_name(self):
        """ returns a string that indicates the name of the
            day of the week of the Date object that calls it
        """
        d1 = Date(11,12,2018)
        day_names = ['Monday', 'Tuesday', 'Wednesday',
                     'Thursday', 'Friday', 'Saturday', 'Sunday']
        remainder = self.days_between(d1)
        if remainder % 7 == 0:
            return day_names[0]
        elif remainder % 7 ==1:
            return day_names[1]
        elif remainder % 7 ==2:
            return day_names[2]
        elif remainder % 7 == 3:
            return day_names[3]
        elif remainder % 7 ==4:
            return day_names[4]
        elif remainder % 7 ==5:
            return day_names[5]
        else:
            return day_names[6]

