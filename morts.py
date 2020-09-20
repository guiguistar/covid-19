# coding utf-8

import matplotlib.pyplot as plt
import numpy as np
import sys

from datetime import datetime

jours = ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi']
mois = ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre']
mois_ = ['Jan.', 'Fév.', 'Mars', 'Avr.', 'Mai', 'Juin', 'juil.', 'Août', 'Sep.', 'Oct.', 'Nov.', 'Déc.']

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
        iDeb = max(i - largeur // 2, 0)
        iFin = min(i + (largeur - largeur // 2), len(X))
        M = np.mean(X[iDeb:iFin])
        moyennes.append(M)
        
    return moyennes    

def graphiques(dates_morts, nombres_morts, dates_cas, nombres_cas):
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

    nombres_morts_lisses = moyennes_glissantes(nombres_morts, largeur=20)
    nombres_cas_lisses = moyennes_glissantes(nombres_cas, largeur=20)
    
    abscisses = list(range(len(nombres_morts)))

    debut, fin = 0, -1
    marqueurs = len(nombres_cas) * ['+']
    couleurs = ['red', 'blue', 'green', 'pink', 'cyan', 'yellow', 'gray', 'black']

    f = lambda date: int(date[2:4]) - 2
    
    c = [couleurs[f(date)] for date in dates_morts[:-1]]

    res = list(zip(dates_morts, nombres_morts, nombres_cas))
            
    filtre = []
    for i in range(8):
        liste = list(filter(lambda x: f(x[0]) == i, res))
        filtre.append(liste)

    #print(*filtre, sep='\n')
        
    figure, axes = plt.subplots(2)

    figure.canvas.set_window_title('Covid-19')

    axes[0].set_title('Nombre de cas et nombre de morts en fonction du temps')
    axes[0].set_xlabel('Temps (jours)')
    axes[0].plot(abscisses, nombres_morts, label='Nombre de morts', c='orange', linewidth=0.5)
    axes[0].plot(abscisses, nombres_morts_lisses, label='Nombre de morts', c='orange', linewidth=1)
    axes[0].plot(abscisses, nombres_cas, label='Nombre de cas', c='blue', linewidth=0.56)
    axes[0].plot(abscisses, nombres_cas_lisses, label='Nombre de cas', c='blue', linewidth=1)

    axes[0].legend()
    
    for i in range(8):
        la, lb, lc = [], [], []
        for A, B, C in filtre[i]:
            la.append(A)
            lb.append(B)
            lc.append(C)
        print(la, len(la))
        print(lb, len(lb))
        print(lc, len(lc))

        axes[1].scatter(lc, lb, marker='+', label=mois_[i+1], linewidth=0.5)

    
    axes[1].legend(ncol=2)
    axes[1].set_title('Corrélation nombre de cas / nombre de morts')
    axes[1].set_xlabel('Nombre de cas')
    axes[1].set_ylabel('Nombre de morts')
    #axes[1].scatter(nombres_cas[debut:fin], nombres_morts[debut:fin], marker='+', c=c, linewidth=0.5)

    plt.tight_layout()
    plt.show()
    
if __name__ == '__main__':
    if not len(sys.argv) == 3:
        print('Le script a besoin de deux paramètres correspondant aux deux fichiers.')
        sys.exit()

    _, nomFichierMorts, nomFichierCas = sys.argv
        
    dates_morts, nombres_morts  = lire_fichier(nomFichierMorts)
    dates_cas, nombres_cas  = lire_fichier(nomFichierCas)
    
    graphiques(dates_morts, nombres_morts, dates_cas, nombres_cas)
