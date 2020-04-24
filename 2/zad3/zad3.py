def mymax(iterable, key = lambda x: x):
    max_el = max_key = None
    for el in iterable:
        if (max_key == None or key(el) > max_key):
            max_el = el
            max_key = key(el)     
    return max_el

maxint = mymax([1, 3, 5, 7, 4, 6, 9, 2, 0])
maxchar = mymax("Suncana strana ulice")
maxstring = mymax([
  "Gle", "malu", "vocku", "poslije", "kise",
  "Puna", "je", "kapi", "pa", "ih", "njise"])
D = {'burek':8, 'buhtla':5}
maxfood = mymax(D, lambda x: D[x])
persons = [("Luka", "Giljanović"), ("Mislav", "Has"), ("Darijo", "Brčina"), ("Mislav", "has")]
maxperson = mymax(persons)
print(maxint)
print(maxchar)
print(maxstring)
print(maxfood)
print(maxperson)
