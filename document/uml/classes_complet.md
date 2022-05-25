@startuml classes_global_ok

skinparam ClassFontColor automatic
skinparam ClassAttributeFontColor automatic
skinparam ClassFontSize 20
skinparam ClassFontStyle bold
skinparam ClassAttributeFontSize 14
skinparam ClassAttributeIconSize 14
skinparam ClassStereotypeFontColor automatic

skinparam TitleBorderRoundCorner 10
skinparam TitleBorderThickness 2
skinparam TitleFontSize 30
skinparam TitleFontStyle bold
skinparam TitleBackgroundColor lightcyan

skinparam Padding 2

title MiniEclipse : Diagramme de classes\n\t\tUtilisation principale, gestion de projet, réification\t\t

legend bottom right 
UML : Diagramme de clasees
Version : 1.0
Dernière révision : 29/03/2022
L'équipe MiniEclipse
endlegend

' -------------- Modèle de réification pour affichage -------------- 

package "Réification pour affichage" <<rectangle>> {
    class Package #white {
        - nom : String
    }

    class Classe #cyan {
    }

    abstract class Declarable #yellow {
        - nom : String
    }

    abstract class Type #cyan {
    }

    class "TypeSimple" as TypeDéfaut #LightCyan {
        - Visibilite = public
        - modificateurs = aucun
    }

    class Attribut #LightGreen {
        - type : Type
    }

    abstract class Executable #orange {

    }

    class Methode #orange {
        - type retour : Type
    }

    class Parametre #LightGray {
        - nom : String
        - type : Type
    }

    class Constructeur #orange {

    }
    
    enum Visibilite #DarkGray {
        PRIVATE
        PUBLIC
        PROTECTED
        PACKAGE
        + getCode() : String
        + getIcone() : String
    }
    
    enum Modificateur #DarkGray {
        ABSTRACT
        STATIC
        FINAL
        INTERFACE
        + getCode() : String
        + getIcone() : String
    }

    note bottom of Type
    (PlantUML ne laisse pas la place
    donc c'est écrit en note)
    --
    Liens entre Classe et Type
    (hors héritage) :

    L'un est 0..1 extends et 
    l'autre est 0..* implements
    endnote
}

Executable -r-|> Declarable
Type -u-|> Declarable
TypeDéfaut -l-|> Type
Classe --- Package
Classe "0..*" o-l- Attribut
Attribut -r-|> Declarable
Classe o-l- "0..*" Methode
Executable o-u- "0..*" Parametre
Methode -u--|> Executable
Methode -u[hidden]- Constructeur
Classe "0..*" o-l- Constructeur
Constructeur -u-|> Executable
Classe <-r- Type
Classe -r-|> Type
Classe <-r- Type
Declarable "0..*" <-u- Modificateur
Declarable "\t\t1" <-u- Visibilite


' -------------- Coeur de l'application --------------

package "Core : gestion de projet" <<rectangle>> {
    abstract class Noeud #LightCyan {
        - chemin complet : String
        - nom : String : déduit automatiquement
        + {abstract} getArborescence() : Arborescence
        + {abstract} refresh() : void
    }

    abstract class Conteneur #LightSkyBlue {
        + getArborescence() : Arborescence
        + getArborescence(profondeur : int) : Arborescence
    }

    class Dossier #RoyalBlue {
        + populate() : void
        + getSousDossiers() : List<Dossier>
        + getFichiers() : List<Fichier>
    }

    class Fichier #PaleGreen {
        - contenu : byte[]
        - encodage : String : si textuel
        - typeSpecifique : ItemType : à voir si besoin
    }

    class Arborescence #Peru {
        - type : ItemType
        - nom : String
        --
        Différentes Methodes 
        selon les besoins
    }

    enum ItemType #DarkGray {
        FICHIER
        DOSSIER
        PACKAGE
        + getCode() : String
        + getIcone() : String
    }

    class Projet #LightPink {
        - configuration : type à déterminer
        + {static} creer(racine : Dossier) : Projet
        + {static} ouvrir(racine : Dossier) : Projet
        + oublier() : void
        + compiler() : void
        + exécuter() : void
    }

    class Workspace <<Serializable, Singleton>> #DarkRed {
        - {static} instance : Workspace
        - Workspace()
        + {static} getWorkspace : Workspace
        + {static} toCache() : void
        + {static} reset() : void
    }
}
Conteneur -l-|> Noeud
Dossier -l-|> Conteneur
Fichier -l-|> Noeud
Arborescence "1" <-d- Noeud : - handle
Arborescence o-r- "\t0..*" Arborescence : - filles
Fichier -d[hidden]- Conteneur
Noeud -l[hidden]- ItemType
Projet "\n\n\n\n1" <-u- Dossier : - racine
'Projet "\t0..1" <-u- Fichier : - main
Workspace o-r- Projet : - projets
Conteneur "0..*" o-l- Noeud : - filles

' -------------- Liens entre les deux diagrammes --------------
Fichier <-- Classe
Package -|> Dossier

@enduml
