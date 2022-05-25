@startuml
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
        interface ArboItem #LightGray {
        - getArboType() : String
        - getArboNom() : String
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
     
    note bottom of ArboItem 
    Interface permettant de generaliser 
    les objets contenu dans l'arborescence
    de classe pour une meilleure gestion
    du code et un potentiel d'évolution
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
Classe --|> ArboItem
Methode -|> ArboItem
Attribut -|> ArboItem
Constructeur -|> ArboItem
Package -|> ArboItem
@enduml
