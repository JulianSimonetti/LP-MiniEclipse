@startuml
actor actor

==Init==
actor -> me.MiniEclipse : <<create>>
activate me.MiniEclipse
participant p.Projet
participant pack.Package
participant c.Classe
participant i.Interface

actor -> me.MiniEclipse : Cree un Projet
me.MiniEclipse -> p.Projet : <<create>>
actor -> me.MiniEclipse : Cree un Package
me.MiniEclipse -> pack.Package : <<create>>
actor -> me.MiniEclipse : Cree une Classe 
me.MiniEclipse -> c.Classe : <<create>>(Package)
actor -> me.MiniEclipse : Cree une Interface 
me.MiniEclipse -> i.Interface : <<create>>(Package)

==Update==

actor -> me.MiniEclipse : Modifier un Package
me.MiniEclipse -> pack.Package : modifier(path)
pack.Package -> me.MiniEclipse : monPath
alt if Classe Existe
me.MiniEclipse -> c.Classe : changePackage()
end
alt if Interface Existe
me.MiniEclipse -> i.Interface : changePackage()
end

actor -> me.MiniEclipse : Modifier une Classe
me.MiniEclipse -> c.Classe : changeCode()

actor -> me.MiniEclipse : Modifier une Interface
me.MiniEclipse -> i.Interface : changeCode()
==Suppression==

actor -> me.MiniEclipse : Supprimer un Projet
me.MiniEclipse -> p.Projet : <<destroy>>
me.MiniEclipse -> pack.Package : <<destroy>>
alt if Classe Existe
me.MiniEclipse -> c.Classe : <<destroy>>
end
alt if Interface Existe
me.MiniEclipse -> i.Interface : <<destroy>>
end

actor -> me.MiniEclipse : Supprimer Package
me.MiniEclipse -> pack.Package : <<destroy>>
alt if Classe Existe
me.MiniEclipse -> c.Classe : <<destroy>>
end
alt if Interface Existe
me.MiniEclipse -> i.Interface : <<destroy>>
end

actor -> me.MiniEclipse : Supprimer une Classe 
me.MiniEclipse -> c.Classe : <<destroy>>
actor -> me.MiniEclipse : Supprimer une Interface 
me.MiniEclipse -> i.Interface : <<destroy>>
@enduml