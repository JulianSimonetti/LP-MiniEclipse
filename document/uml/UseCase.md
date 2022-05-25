@startuml
left to right direction
actor Utilisateur as U
rectangle "Gestion De Projet"{
	usecase "Creation du projet" as UC1
	usecase "Suppression du projet" as UC2
	usecase "Execution du projet" as UC3
	usecase "Ajout de classe" as UC4
	usecase "Modification de classe" as UC5
	usecase "Suppression de classe" as UC6
	(UC2) ..> (UC1) : include
	(UC3) ..> (UC1) : include
	(UC5) ..> (UC4) : include
	(UC6) ..> (UC4) : include
}
U -- UC5
U -- UC6
U -- UC2
U -- UC3
@enduml