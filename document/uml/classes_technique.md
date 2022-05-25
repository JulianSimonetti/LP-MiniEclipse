@startuml
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
@enduml
