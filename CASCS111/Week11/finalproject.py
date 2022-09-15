import math

def clean_text(txt):
        """ returns a list containing the words in txt after it has been “cleaned”
            input txt: any string of text
        """
        list_of_string = []
        str = ''
        count = 0
        for x in txt:
            x = x.lower()
            count += 1
            if count == len(txt):
                list_of_string += [str]
            elif x == ' ':
                list_of_string += [str]
                str = ''
            else:
                if x not in 'qwertyuiopasdfghjklzxcvbnnmQWERTYUIOPASDFGHJKLZXCVBNM':
                    x = x.replace(x, '')
                str += x
        return list_of_string

def sample_file_write(filename):
    """A function that demonstrates how to write a
       Python dictionary to an easily-readable file.
    """
    d = {'test': 1, 'foo': 42}   # Create a sample dictionary.
    f = open(filename, 'w')      # Open file for writing.
    f.write(str(d))              # Writes the dictionary to the file.
    f.close()

def sample_file_read(filename):
    """A function that demonstrates how to read a
       Python dictionary from a file.
    """
    f = open(filename, 'r')    # Open for reading.
    d_str = f.read()           # Read in a string that represents a dict.
    f.close()

    d = dict(eval(d_str))      # Convert the string to a dictionary.

    print("Inside the newly-read dictionary, d, we have:")
    print(d)

def stem(s):
    """ return the stem of s (parameter variable)
        input s: any string of characters
    """
    if s[-4:] == 'iest':
        s = s[:-4] + 'y'
        stem_rest = stem(s)
        s = stem_rest
    elif s[-3:] == 'ies':
        s = s[:-3] + 'y'
        stem_rest = stem(s)
        s = stem_rest
    elif s[-2:] == 'es':
        s = s[:-2]
        stem_rest = stem(s)
        s = stem_rest
    elif s[-1:] == 's':
        if len(s) == 1:
            s = s
        elif len(s) <= 2:
            s = s
        elif s[-2:] == 'is':
            s = s
        else:
            s = s[:-1]
            stem_rest = stem(s)
            s = stem_rest
    elif s[-3:] == 'est':
        if len(s) <= 5:
            s = s
        elif s[-5:-3] == 'er':
            s = s
        elif len(s) >= 6:
            if s[-4] == s[-5] and s[-6] in 'aeiouAEIOU':
                s = s[:-4]
                stem_rest = stem(s)
                s = stem_rest
        else:
            s = s[:-3]
            stem_rest = stem(s)
            s = stem_rest
    elif s[-3:] == 'ier':
        s = s[:-3] + 'y'
        stem_rest = stem(s)
        s = stem_rest
    elif s[-2:] == 'er':
        if len(s) >= 5:
            if s[-3] == s[-4] and s[-5] in 'aeiouAEIOU':
                s = s[:-3]
        else:
            s = s[:-2]
            stem_rest = stem(s)
            s = stem_rest
    elif s[-2:] == 'ed':
        if len(s) >= 5:
            if s[-3] == s[-4] and s[-5] in 'aeiouAEIOU':
                s = s[:-3]
        else:
            s = s[:-2]
            stem_rest = stem(s)
            s = stem_rest
    elif s [-3:] == 'ing':
        if len(s) == 4:
            s = s
        elif len(s) >= 6:
            if s[-4] == s[-5] and s[-6] in 'aeiouAEIOU':
                s = s[:-4]
                stem_rest = stem(s)
                s = stem_rest
        else:
            s = s[:-3]
            stem_rest = stem(s)
            s = stem_rest
    elif s[-4:] == 'able' or s[-4:] == 'ible':
        s = s[:-4]
        stem_rest = stem(s)
        s = stem_rest
    elif s[-3:] == 'ily':
        if len(s) >= 6:
            if s[-4] == s[-5] and s[-6] in 'aeiouAEIOU':
                s = s[:-4]
        else:
            s = s[:-3] + 'y'
            stem_rest = stem(s)
            s = stem_rest
    elif s[-2:] == 'ly':
        s = s[:-2]
        stem_rest = stem(s)
        s = stem_rest
    elif s[:3] == 'pre':
        s = s[3:]
        stem_rest = stem(s)
        s = stem_rest
    elif s[:3] == 'mis':
        if len(s) == 4:
            s = s
        else:
            s = s[3:]
            stem_rest = stem(s)
            s = stem_rest
    elif s[:-4] == 'sion' or s[:-4] == 'tion':
        s = s[:-4]
        stem_rest = stem(s)
        s = stem_rest
    elif s[:4] == 'over':
        if len(s) == 4:
            s = s
        else:
            s = s[4:]
            stem_rest = stem(s)
            s = stem_rest
    elif s[:5] == 'under':
        if len(s) == 5:
            s = s
        else:
            s = s[5:]
            stem_rest = stem(s)
            s = stem_rest
    
    return s

def compare_dictionaries(d1,d2):
    """ return their log similarity score
        input d1: dictionary input with the value(key:value) being any number
        input d2: dictionary input with the value(key:value) being any number
    """
    log_sim_score = 0
    total = 0
    for x in d1:
        total += d1[x]
    for key in d2:
        if key in d1:
            log_sim_score += d2[key] * math.log(d1[key]/total)
        else:
            log_sim_score += d2[key] * math.log(0.5/total)
    return log_sim_score

def test():
    """ the test for TextModel scoring """
    source1 = TextModel('source1')
    source1.add_string('It is interesting that she is interested.')

    source2 = TextModel('source2')
    source2.add_string('I am very, very excited about this!')

    mystery = TextModel('mystery')
    mystery.add_string('Is he interested? No, but I am.')
    mystery.classify(source1, source2)

def run_tests():
    """ my test for TextModel scoring, which compares whether my work
        is more like works from rowling or works from Shakespeare
    """
    source1 = TextModel('rowling')
    source1.add_file('JKRowling.txt')

    source2 = TextModel('shakespeare')
    source2.add_file('Shakespeare.txt')

    mystery = TextModel('NamesakeEssay')
    mystery.add_file('theNamesake.txt')
    mystery.classify(source1, source2)

    print()
    print()

    mysteryTwo = TextModel('ShakespeareFourtySonnets')
    mysteryTwo.add_file('fourtySonnets.txt')
    mysteryTwo.classify(source1, source2)

    print()
    print()


    mysteryThree = TextModel('HarryPotterChapterFour')
    mysteryThree.add_file('ChapterFour.txt')
    mysteryThree.classify(source1, source2)

    print()
    print()


    mysteryFour = TextModel('Mockingbird')
    mysteryFour.add_file('mockingbird.txt')
    mysteryFour.classify(source1,source2)

class TextModel:
    """ blueprint for objects that model a body of text """

    def __init__(self,model_name):
        """ constructs a new TextModel object
            input model_name: any string
        """
        self.name = model_name
        self.words = ({})
        self.word_lengths= ({})
        self.stems = ({})
        self.sentence_lengths = ({})
        self.number_of_vowels = ({})

    def __repr__(self):
        """ returns a string that includes the name of the model as well as
            the sizes of the dictionaries for each feature of the text
        """
        string = "text model name: " + self.name + '\n' 
        string += '  number of words: ' + str(len(self.words)) + '\n'
        string += '  number of word lengths: ' + str(len(self.word_lengths)) + '\n'
        string += '  number of stems: ' + str(len(self.stems)) + '\n'
        string += '  number of sentence lengths: ' + str(len(self.sentence_lengths)) + '\n'
        string += '  number of vowels: ' + str(len(self.number_of_vowels))
        return string

    def add_string(self, s):
        """ adds a string of text s to the model by augmenting the feature
            dictionaries defined in the constructor
            input s: string of text
        """

        word_list = clean_text(s)
        for w in word_list:
            if w in self.words:
                self.words[w] += 1
            else:
                self.words[w] = 1
                
            number = len(w)
            if number in self.word_lengths:
                self.word_lengths[number] += 1
            else:
                self.word_lengths[number] = 1
    
            stem_word = stem(w)
            if stem_word in self.stems:
                self.stems[stem_word] += 1
            else:
                self.stems[stem_word] = 1

        count_a = 0
        count_e = 0
        count_i = 0
        count_o = 0
        count_u = 0
        
        for x in s:
            if x == 'a':
                count_a += 1
            elif x == 'e':
                count_e += 1
            elif x == 'i':
                count_i += 1
            elif x == 'o':
                count_o += 1
            elif x == 'u':
                count_u += 1

        if count_a == 0:
            count_a += 0.01
        if count_e == 0:
            count_e += 0.01
        if count_i == 0:
            count_i += 0.01
        if count_o == 0:
            count_o += 0.01
        if count_u == 0:
            count_u += 0.01
        self.number_of_vowels['a'] = count_a
        self.number_of_vowels['e'] = count_e
        self.number_of_vowels['i'] = count_i
        self.number_of_vowels['o'] = count_o
        self.number_of_vowels['u'] = count_u
            
        count = 0
        count_two = 0
        count_list = []
        for x in s:
            count_two +=1
            if count_two == len(s):
                count_list += [1]
            if x == ' ':
                count += 1
                count_two = 0
            if x == '.' or x == '!' or x == '?':
                count += 1
                count_list += [count]
                count = 0
                count_two = 0
        for number in count_list:
            if number in self.sentence_lengths:
                self.sentence_lengths[number] += 1
            else:
                self.sentence_lengths[number] = 1


    def add_file(self, filename):
        """ adds all of the text in the file identified by filename to the model
            input filename: any file
        """
        f = open(filename, 'r', encoding='utf8', errors='ignore')
        self.add_string(f.read())
        
    def save_model(self):
        """ saves the TextModel object self by writing its various feature
            dictionaries to files
        """
        the_string = ''
        for x in self.name:
            if x.isupper() == True:
                the_string += x
        filenameOne = the_string + '_words'
        filenameTwo = the_string + '_word_lengths'
        filenameThree = the_string + '_stems'
        filenameFour = the_string + '_sentence_lengths'
        filenameFive = the_string + '_number_of_vowels'
        d = self.words  # Create a sample dictionary.
        f = open(filenameOne, 'w')      
        f.write(str(d))
        d = self.word_lengths
        f = open(filenameTwo, 'w')
        f.write(str(d))
        d = self.stems
        f = open(filenameThree, 'w')
        f.write(str(d))
        d = self.sentence_lengths
        f = open(filenameFour, 'w')
        f.write(str(d))
        d = self.number_of_vowels
        f = open(filenameFive, 'w')
        f.write(str(d))
        f.close()

    def read_model(self):
        """ reads the stored dictionaries for the called TextModel object from
            their files and assigns them to the attributes of the
            called TextModel
        """

        the_string = ''
        for x in self.name:
            if x.isupper() == True:
                the_string += x
        filenameOne = the_string + '_words'
        filenameTwo = the_string + '_word_lengths'
        filenameThree = the_string + '_stems'
        filenameFour = the_string + '_sentence_lengths'
        filenameFive = the_string + '_number_of_vowels'
        
        f = open(filenameOne, 'r')    # Open for reading.
        d_str = f.read()           
        f.close()

        d = dict(eval(d_str))

        f_two = open(filenameTwo, 'r')
        d_str_two = f_two.read()
        f_two.close()

        d_two = dict(eval(d_str_two))

        f = open(filenameThree, 'r')    # Open for reading.
        d_str_three = f.read()           
        f.close()

        d_three = dict(eval(d_str_three))

        f = open(filenameFour, 'r')    # Open for reading.
        d_str_four = f.read()           
        f.close()

        d_four = dict(eval(d_str_four))


        f = open(filenameFive, 'r')    # Open for reading.
        d_str_five = f.read()           
        f.close()

        d_five = dict(eval(d_str_five))
        
        self.words = d
        self.word_lengths = d_two
        self.stems = d_three
        self.sentence_lengths = d_four
        self.number_of_vowels = d_five

    def similarity_scores(self, other):
        """ returns a list of log similarity scores measuring
            the similarity of self and other
            input other: other TextModel object
        """
        listing = []
        word_score = compare_dictionaries(other.words, self.words)
        listing += [word_score]
        word_lengths_score = compare_dictionaries(other.word_lengths, self.word_lengths)
        listing += [word_lengths_score]
        stems_score = compare_dictionaries(other.stems, self.stems)
        listing += [stems_score]
        sentence_lengths_score = compare_dictionaries(other.sentence_lengths, self.sentence_lengths)
        listing += [sentence_lengths_score]
        number_of_vowels = compare_dictionaries(other.number_of_vowels, self.number_of_vowels)
        listing += [number_of_vowels]
        return listing

    def classify(self, source1, source2):
        """ compares the called TextModel object (self) to two other
            “source” TextModel objects (source1 and source2) and determines
            which of these other TextModels is the more likely
            source of the called TextModel
            input source1: first 'source' TextModel obejct
            input source2: second 'source' TextModel object
        """
        scores1 = self.similarity_scores(source1)
        scores2 = self.similarity_scores(source2)

        print("scores for " + source1.name + ": " + str(scores1))
        print("scores for " + source2.name + ": " + str(scores2))

        count_one = 0
        count_two = 0
        for i in range(len(scores1)):
            if scores1[i] > scores2[i]:
                count_one += 1
            elif scores1[i] < scores2[i]:
                count_two += 1
            else:
                count_two += 1
                count_one += 1
        if count_one > count_two:
            print(self.name + " is more likely to have come from " + source1.name)
        else:
            print(self.name + " is more likely to have come from " + source2.name)
        







    
