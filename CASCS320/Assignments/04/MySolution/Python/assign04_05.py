########################
# HX-2023-06-20: 30 points
########################
"""
Given a history of wordle hints, this function returns a
word as the player's guess. THIS GUESS SHOULD NOT CONTRADICT
ANY ONE OF THE HINTS GIVEN.
"""
########################################################################
def wordle_guess(hints):
    print("")
    print(hints)
    print("  ")
    print("new word given")
    print("  ")
    word = ""
    allLetter2 = ["a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"]

    allLetter = ["a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"]
    wordReplace = [[[1],"?",1]]
    index = 0
    length = 0
    for x in hints:
        length = len(x)
    word = "_" * length
    shouldAppend = True
    count = 0
    alphabet_count = [["?",1]]
    final_alpha = []
    Add = True
    for x in hints:
        for y in range(len(word)):
            Add = True
            number = x[y][0]

            letter = x[y][1]
            
            if number >= 1:
                for c in range(len(alphabet_count)):
                    if alphabet_count[c][0] == letter:
                        alphabet_count[c][1] += 1 
                        Add = False
            if Add == True:
                alphabet_count.append([letter, 1])


            if number == 1:
                word = word[:y] + letter + word[y+1:]

            elif number == 0:
                if letter in allLetter:
                    allLetter.remove(letter)
                if word[y] not in allLetter2 and word[y] != "-":
                    word = word[:y] + "0" + word[y+1:]
            else:
                if word[y] not in allLetter2:
                    word = word[:y] + "-" + word[y+1:]
                for ab in range(len(wordReplace)):
                    if letter == wordReplace[ab][1]:
                        shouldAppend = False
                        if index not in wordReplace[ab][0]:
                            wordReplace[ab][0].append(index)
                if shouldAppend == True:
                    wordReplace.append([[index],letter,1])
            
            shouldAppend = True
            index +=1


        for x in alphabet_count:
            if x[1] > 1:
                final_alpha.append(x)
        alphabet_count = []
        index = 0
        Add = True


    final_alpha = [list(t) for t in {tuple(lst) for lst in final_alpha}]
    number = 0
    number2 = 0

    for x in final_alpha:
        for y in wordReplace:
            if x[0] == y[1]:
                y[2] = y[2] -1 + x[1]
                

    for x in word:
        if "-" == x:
            number2 += 1
    for x in word:
        if "0" == x:
            number += 1


    if number2 == 0 and number == 0:
        return word

    for x in range(len(word)):
        if word[x] == "0":
            word = word[:x] + allLetter[0] + word[x+1:]
    
    wordReplace = wordReplace[1:]

    for x in word:
        for y in wordReplace:
            if x == y[1]:
                y[2] -= 1
    wordReplace = [elem for elem in wordReplace if elem[2] != 0]


    word = guess_helper(word, wordReplace, hints)
    return word

def guess_helper(word,wordReplace, hints):
    index = 0
    count = 0
    cannotRun = True
    for x in range(len(word)):
        if word[x] == "-":
            for y in wordReplace:
                cannotRun = True
                if x not in y[0]:
                    for aa in hints:
                        for bb in range(len(aa)):
                            
                            if aa[bb][0] == 0 and aa[bb][1] == y[1] and index == count:
                                cannotRun = False
                            count += 1
                        count = 0
                    if cannotRun == True:
                        word = word[:x] + y[1] + word[x+1:]     
                        y[2] -= 1                   
                        wordReplace = [elem for elem in wordReplace if elem[1] != y[1] and elem[2] != 0 ]
                        cannotRun = True
                        break
                    else:
                        cannotRun = True
                        continue
        index +=1         
                    
                        
    return word



########################################################################
