#!/usr/bin/python

txt = """QUOTE:","hoola","asdd'hol'ds",QUOTE:|,|holas|,|chaos con " dobles |"""

def parsetext(txt):
    st = 0
    currentquote = None
    while (len (txt[st:st + 1]) > 0 ):
        if currentquote is None:
            #if not txt[st:st + 6] == 'QUOTE:':
                #print("quote not found")
            endindex = txt.find( ',', st + 6 )
            currentquote = txt[st + 6:endindex]
            #print('currentquote %s' % currentquote)
            st = endindex
        else:
            if (txt[st:st+1] == currentquote):
                endindex = txt.find(currentquote, st + 1)
                print(txt[st+1:endindex])
                st = endindex + 1 
            elif (txt[st:st+1] == ',' ):
                st = st + 1
            elif (txt[st:st + 6] == 'QUOTE:' ):
                endindex = txt.find( ',', st + 6 )
                currentquote = txt[st + 6:endindex]
                #print('currentquote %s' % currentquote)
                st = endindex
            #else:
                #print("error")
                
parsetext(txt)
