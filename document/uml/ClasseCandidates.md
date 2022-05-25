@startuml

class Classe{
	- nom 
	- estAbstrait
}

class Attribut{
	- nom 
	- type 
	- valeur 
}
enum Visibilite{
	PRIVATE
	PUBLIC
	PROTECTED
}
class Methode{
	- typeRetour 
}

class Constructeur{
}

class Package {
	- nom
}

abstract class Executable #orange{
	- nom 
	- code 
}
class Parametre<T>{
	- nom
	- type : Class
	- valeur : Object ?? T ??
}

Executable o-- Visibilite
Attribut o-- Visibilite
Executable <|-- Constructeur
Executable <|-- Methode
Executable o- "0..n" Parametre
Classe o--"0..n" Attribut
Classe o--"0..n" Executable

Package -- "0..n" Classe

Classe - "0..1" Classe
note right of Classe
	estAbstrait : facteur d'instanciation
end note

note top of Executable
	Sert à factoriser Methode et Constructeur
end note
@enduml