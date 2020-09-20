# coding utf-8

import matplotlib.pyplot as plt
import numpy as np
import sys

from datetime import datetime

jours = ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi']
mois = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre']

def format_date(DDMMYYYY):
    #print(f"=====>> '{DDMMYYYY}'")
    dt = datetime.strptime(DDMMYYYY, "%d%m%Y")
    annee = dt.strftime("%Y")
    mois_ = dt.strftime("%m")
    jour = dt.strftime("%d")
    num_jour = dt.strftime("%w")

    return f"{jours[int(num_jour)]} {jour} {mois[int(mois_)-1]} {annee}"

def lire_fichier(nomFichier):
    dates = []
    nombres = []
    with open(nomFichier) as f:
        ligne = f.readline()[:-1]
        while ligne:
            print(ligne)
            date, nombre = ligne.split(',')
            dates.append(date)
            nombres.append(nombre)
            ligne = f.readline()[:-1]
    return dates, nombres

def lister_dates_manquantes(dates, nombres):
    dates_nombres = zip(dates, nombres)
    dates_manquantes = [format_date(date) for date, nombre in dates_nombres if nombre == '0000']

    return dates_manquantes

def coeff_corr_glissant(X, Y, multiplicateur=1, largeur=30):
    assert len(X) == len(Y)

    correlations = []
    """
    for i in range(len(X)):
        if i < largeur:
            correlations.append(0)
        else:
            C = np.corrcoef([X[i-largeur:i], Y[i-largeur:i]])
            correlations.append(multiplicateur * C[0][1])
    """
    for i in range(len(X)):
        if i < len(X) - largeur:
            C = np.corrcoef([X[i:i+largeur], Y[i:i+largeur]])
            correlations.append(multiplicateur * C[0][1])
        else:
            correlations.append(0)

    return correlations

def moyennes_glissantes(X, largeur=30):
    moyennes = []

    for i in range(len(X)):
        iFin = min(i + largeur, len(X))
        M = np.mean(X[i:iFin])
        moyennes.append(M)
        
    return moyennes
    

if __name__ == '__main__':
    if not len(sys.argv) == 3:
        print('Le script a besoin de deux paramètres correspondant aux deux fichiers.')
        sys.exit()

    _, nomFichierMorts, nomFichierCas = sys.argv
        
    dates_morts, nombres_morts  = lire_fichier(nomFichierMorts)
    dates_cas, nombres_cas  = lire_fichier(nomFichierCas)
    
    print(nombres_morts)
    print(nombres_cas)

    #dates_nombres = zip(dates_morts, nombre_morts)
    #dates_manquantes = [format_date(date) for date, nombre in dates_nombres if nombre == '0000']

    dates_manquantes_morts = lister_dates_manquantes(dates_morts, nombres_morts)
    dates_manquantes_cas = lister_dates_manquantes(dates_cas, nombres_cas)
    
    #print(*dates_manquantes, len(dates_manquantes), sep='\n')
    print(len(dates_morts), len(dates_cas))

    nombres_morts = list(map(int,nombres_morts))
    nombres_cas = list(map(lambda x: 1 * int(x), nombres_cas))

    nombres_morts = moyennes_glissantes(nombres_morts, largeur=20)
    nombres_cas = moyennes_glissantes(nombres_cas, largeur=20)
    
    correlations = coeff_corr_glissant(nombres_morts, nombres_cas, multiplicateur=3000, largeur=40)
    
    abscisses = list(range(len(nombres_morts)))
    
    #plt.plot(abscisses, nombres_morts, abscisses, nombres_cas, abscisses, correlations)
    plt.plot(abscisses, nombres_morts, abscisses, nombres_cas)

    debut, fin = 0, -1
    #plt.plot(nombres_cas[debut:fin], nombres_morts[debut:fin], '+')
    
    #ax = plt.gca()
    #ax.axes.xaxis.set_ticklabels([])

    plt.show()
