@startuml
(*) --> "Executer le projet"
if "Est déjà compiler" then
    -->[true] "Executer les classes compilés"
    else
    ->[false] "Compiler le projet"
    endif
    --> "Executer les classes compilés"
--> (*)
@enduml