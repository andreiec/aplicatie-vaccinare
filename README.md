# Aplicație vaccinare
## Prezentare generală
Aplicație pentru programarea vaccinării împotriva virusului SARS-CoV-2 realizată ca proiect pentru materia "Metode de dezvoltare software", Facultatea de Matematică și Informatică, Universitatea din București.
### Componența echipei
* [Băiaș Bogdan-Andrei](https://github.com/AndreiBaias) - BA + QA 
* [Cociobanu Victor-Andrei](https://github.com/Vikcoc) - Back end developer + QA
* [Constantinescu Andrei-Eduard](https://github.com/andreiec) - Front end developer
* [Tudose Andrei-Daniel](https://github.com/andreitudose2000) - Back end developer
### Motivarea alegerii temei
Echipa noastră a creat această aplicație deoarece tema ei este una de actualitate, aplicația noastră fiind creată în scopul facilitării procesului de vaccinare, atât pentru actorul administrativ, cât și pentru medici și pacienți. Aplicația noastră aduce utilizatorului ușurință de utilizare, claritate și control asupra entității sale virtuale.
### [Demo]()
## Descrierea aplicației
Aplicație Android care permite utilizatorului să se informeze despre infectarea cu virusul SARS-CoV-2 și despre vaccinarea împotriva acestui virus și să se programeze pentru vaccinare. Utilizatorul poate afla motive pentru care să se vaccineze, statistici, detalii despre evoluția bolii și tratament, metode de a se proteja, numere de telefon utile și sfaturi pentru gestionarea contextului pandemic actual. Utilizatorul poate accesa o harta a României unde să vadă toate centrele de vaccinare și să acceseze un astfel de centru pentru a se programa la vaccinare. Utilizatorul își poate vedea profilul său, care conține detalii despre contul său și despre programările lui, dar si un cod QR asociat contului. Aplicația permite și actualizarea datelor personale ale utilizatorului dacă acesta constată că a făcut o greșeala când a creat contul. Odata finalizată programarea pentru vaccinarea cu prima doză (acolo unde este cazul), programarea pentru doza rapel se face automat in funcție de tipul de vaccin care va fi administrat. 
## Backlog creation
Pentru crearea backlog-ului și pentru o mai bună organizare am folosit [Trello](https://trello.com/b/epf7fWVB/aplicatie-vaccinare).
### User stories
![image](https://user-images.githubusercontent.com/62658054/121852141-b6281280-ccf7-11eb-86cd-3c6c66ced51f.png)
![image](https://user-images.githubusercontent.com/62658054/121852221-d3f57780-ccf7-11eb-8c8a-d5afb6f956a0.png)
![image](https://user-images.githubusercontent.com/62658054/121852294-f1c2dc80-ccf7-11eb-989e-fa8254705945.png)
![image](https://user-images.githubusercontent.com/62658054/121852163-be804d80-ccf7-11eb-8829-8f0a8736d52f.png)
![image](https://user-images.githubusercontent.com/62658054/121852180-c4762e80-ccf7-11eb-9f04-9544e76ded3f.png)
![image](https://user-images.githubusercontent.com/62658054/121852195-c9d37900-ccf7-11eb-8020-3b2a7d8dfa28.png)
![image](https://user-images.githubusercontent.com/62658054/121852235-db1c8580-ccf7-11eb-8786-286f2d985f09.png)
![image](https://user-images.githubusercontent.com/62658054/121852252-e079d000-ccf7-11eb-98b3-1b89c2334271.png)
![image](https://user-images.githubusercontent.com/62658054/121852262-e5d71a80-ccf7-11eb-924b-5276ca9a2cf6.png)
![image](https://user-images.githubusercontent.com/62658054/121852274-ecfe2880-ccf7-11eb-966d-cf8e8bda6f46.png)
![image](https://user-images.githubusercontent.com/62658054/121852306-f5eefa00-ccf7-11eb-8004-c79eb9b8a239.png)
## Diagrams
* UML
![UML](https://user-images.githubusercontent.com/62658054/121908296-8b5caf00-cd35-11eb-8399-202d31ae9ef8.png)

* Database
![image](https://user-images.githubusercontent.com/62658054/121908468-b515d600-cd35-11eb-8782-9c80f93e1195.png)


## Source control
Pentru crearea aplicației au fost utilizate doua repositories diferite, unul pentru [front end development](https://github.com/andreiec/aplicatie-vaccinare) si unul pentru [back end development](https://github.com/Vikcoc/spring-vaccinare), mai apoi făcandu-se un merge final.
### Commits and merges
Mai jos sunt câteva imagini care conțin o parte din commit-uri și cateva merge-uri facute de echipă. 
![image](https://user-images.githubusercontent.com/62658054/121853970-3e0f1c00-ccfa-11eb-9b7d-396d3de9951e.png)
![image](https://user-images.githubusercontent.com/62658054/121853879-25066b00-ccfa-11eb-8b14-223b7a1adef8.png)
![image](https://user-images.githubusercontent.com/62658054/121854146-73b40500-ccfa-11eb-9b5e-e04053a6dc59.png)
![image](https://user-images.githubusercontent.com/62658054/121854267-9cd49580-ccfa-11eb-8bc6-a4cda16d3318.png)
## Teste automate
Pentru testare s-a folosit @Mock pentru a simula existența centrelor de vaccinare și a tipurilor de vaccin in aceste centre, simulând o bază de date. Au fost implementate 5 teste după cum urmează:
1. Preia toate centrele de vaccinare în jurul coordonatelor long. 0 și lat. 0
  ![image](https://user-images.githubusercontent.com/62658054/121889101-76762080-cd21-11eb-8650-091cd66a4a52.png)
2. Verifică dacă în jurul coordonatelor long. 1 și lat. 1 există centre de vaccinare
  ![image](https://user-images.githubusercontent.com/62658054/121889159-90affe80-cd21-11eb-87a1-4283ba6f2640.png)

3. Verifică dacă adăugarea unui nou centru de vaccinare este validă și dacă prin programarea la vaccinare se modifică detaliile centrului de vaccinare
  ![image](https://user-images.githubusercontent.com/62658054/121889179-986fa300-cd21-11eb-96b0-7222a6a7b55b.png)

4. Verifică dacă poate prelua informații despre un tip de vaccin care nu există și dacă întoarce eroare
  ![image](https://user-images.githubusercontent.com/62658054/121889212-a291a180-cd21-11eb-9357-6c00ed010d98.png)

5. Verifică dacă un centru de vaccinare este salvat (cu succes) în baza de date
  ![image](https://user-images.githubusercontent.com/62658054/121889235-a7eeec00-cd21-11eb-9d37-4c6ab5eab202.png)

Rularea testelor:


![image](https://user-images.githubusercontent.com/62658054/121891688-73c8fa80-cd24-11eb-801a-546c5834ae33.png)


## Bug reporting
Deși în timpul procesului de creație am întâmpinat mai multe bug-uri, voi prezenta aici doar câteva dintre ele, împreună cu rezolvările lor, restul putând fi observate pe [Trello](https://trello.com/b/epf7fWVB/aplicatie-vaccinare)


* Bug:

![image](https://user-images.githubusercontent.com/62658054/121892385-59435100-cd25-11eb-8d50-7134aef3cca5.png)
* [Soluție](https://github.com/Vikcoc/spring-vaccinare/commit/141b6d87c4944fe563a24de033a5d133925af59c)

* Bug:

![image](https://user-images.githubusercontent.com/62658054/121893158-5301a480-cd26-11eb-9f89-a24fe7a69e52.png)

* [Soluție](https://github.com/andreiec/aplicatie-vaccinare/commit/820dddd015e7923ff22b2c8db98d81d3d7e47e0f)

* Bug:

![image](https://user-images.githubusercontent.com/62658054/121893441-a2e06b80-cd26-11eb-9801-24f56eff7fde.png)

* [Soluție](https://github.com/andreiec/aplicatie-vaccinare/commit/369b0cf7ac25277824bc27f8bb1755e881a6022c)


## Build tool
După cum am menționat anterior, proiectul a fost împarțit în front end development și back end development. Am ales să folosim build tools pentru ambele părți ale proiectului pentru a gestiona dependențele și pentru a construi aplicația.
- Pentru front end development a fost utilizat [Android Studio](https://developer.android.com/), iar, ca build tool, echipa noastră a decis să utilizeze [Gradle](https://gradle.org/).
- Pentru back end development a fost utilizat IDE-ul [IntelliJ](https://www.jetbrains.com/idea/), iar, ca build tool, echipa noastră a decis să utilizeze [Maven](https://maven.apache.org/).


## Refactoring 
[Exemplu](https://github.com/Vikcoc/spring-vaccinare/commit/99784cb76545a21f2ffd23dff375acf2de539bec)

În loc existe structura try-catch în fiecare controller, prin refactoring această structură a fost înlocuită cu un [Exception Handler](https://github.com/Vikcoc/spring-vaccinare/blob/master/src/main/java/com/vaccin/vaccin/exception/ExceptionHandlerAdvice.java) la nivel de aplicație.

## Design patterns
* Software architectural style : [REST](https://en.wikipedia.org/wiki/Representational_state_transfer)
* Software design pattern : [Singleton](https://github.com/andreiec/aplicatie-vaccinare/commit/4da810682af3638f583c49dfda10a3c93aeaf14c)
