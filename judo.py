import numpy as np
import sys

def read_data(filename):  #read the data
    text=[]
    words=[]
    with open(filename,'r') as f:
        for line in f:
            l=line.replace('\xef\xbb\xbf','').rstrip('\n\r')   #strip all the formatting stuff
            if l != '':   #ignore the empty lines
                text.append(l)
                words.append(l.split(' '))   #split each sentence into words
    words=[i for j in words for i in j]  #flatten
    for i in range(len(words)):words[i]=words[i].strip('`"()!').strip("'").rstrip(',.!:;?)').lower() #remove all the punctuation and make everything lower case
    return text,words

def filt(words,n):
    #filter(lambda x:len(x)==n,words)
    return [x for x in words if len(x)==n]   #list of the words of length n

def make6(words):   #make all the 6 letter words
    one=filt(words,1)
    two=filt(words,2)
    three=filt(words,3) #ugly way to do this
    four=filt(words,4)
    five=filt(words,5)

    new_words=[]
    
    #six letter words are either 1+5,2+4,3+3 both way round
    #make a list of all possible 6 letter words

    for i in range(len(one)):
        new_words.append([s+one[i] for s in five])   #lists of all permuations
        new_words.append([one[i]+s for s in five])

    for i in range(len(two)):
        new_words.append([s+two[i] for s in four])
        new_words.append([two[i]+s for s in four])

    for i in range(len(three)):
        new_words.append([s+three[i] for s in three])

    new_words=[i for j in new_words for i in j]  #flatten

    return new_words

if __name__=="__main__":

    filename=sys.argv[1]
    print 'Reading text from file: ', filename

    try:
        data,words=read_data(filename)  #read the data
    except IOError as e:
        print "I/O error({0}): {1}".format(e.errno, e.strerror)

    unique_words=set(words)   #build a list of unique words
    au_words=sorted(unique_words)  #sort alphabetically
    nocc=[None]*len(au_words)
    for i in range(len(au_words)):nocc[i]=words.count(au_words[i])  #how many times does each word appear
    print 'There are %i unique words in the file\n'% (len(au_words))
    print 'Here is a list of all the unique words in the file alphabetically and the number of times they appear:\n'
    for i in range(len(au_words)): print au_words[i],' occurred ',nocc[i],' times'

    #all the 6 letter words are
    six=filt(au_words,6)

    #six letter words are either 1+5,2+4,3+3 both way round
    #make a list of all possible 6 letter words

    all=make6(au_words)

    print 'Part 2:'

    for i in range(len(six)):
        if all.count(six[i])==1:print six[i]  #does this 6 letter word appear in the list of all possible 6 letter words?




