# coding utf-8

import matplotlib.pyplot as plt
from datetime import datetime

dates = []
nombres = []
jours = ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi']
m = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre']

def format_date(YYYYMMDD):
    dt = datetime.strptime(YYYYMMDD, "%Y%m%d")
    annee = dt.strftime("%Y")
    mois = dt.strftime("%m")
    jour = dt.strftime("%d")
    num_jour = dt.strftime("%w")

    #return f'{jours[int(num_jour)]} {jour} {m[int(mois)-1]} {annee}'
    return f"{jours[int(num_jour)]} {jour} {m[int(mois)-1]} {annee}"

with open('morts_france.csv') as f:
    ligne = f.readline()[:-1]
    while ligne:
        print(ligne)
        date, nombre = ligne.split(',')
        dates.append(date)
        nombres.append(nombre)
        ligne = f.readline()[:-1]

print(nombres)

dates_nombres = zip(dates, nombres)
dates_manquantes = [format_date(date) for date, nombre in dates_nombres if nombre == '0000']

print(*dates_manquantes, len(dates_manquantes), sep='\n')

plt.plot(list(map(int,nombres)))
plt.show()
