******

### Historique des versions

******

# v1.0.0

###### 2026/09/18

* `Note` Apercu de developpement P0: squelette du depot, coeur de messagerie avec tests sur serveur local et identite du plugin pour le centre de plugins AutoJs6. Le contrat Binder, l'API de script et la page de parametres suivent les phases de ROADMAP.md.
* `Fonctionnalité` Identite du plugin `angus-mail` (moteur `mail`) avec le service INFO, la Wake Activity et le squelette du service `org.autojs.plugin.MAIL` pour la decouverte par l'hote
* `Fonctionnalité` Coeur de messagerie sur Eclipse Angus Mail: proprietes de session IMAP / POP3 / SMTP avec SSL ou STARTTLS, authentification par mot de passe et XOAUTH2, envoi SMTP et liste de la boite de reception IMAP, verifies sur un serveur GreenMail local
* `Fonctionnalité` README, instructions du centre de plugins et journal des modifications en 10 langues
* `Dépendance` Eclipse Angus Mail 2.0.5 (`org.eclipse.angus:jakarta.mail`) avec Angus Activation 2.0.3 et Jakarta Activation API 2.1.4
* `Dépendance` Ajout de GreenMail 2.1.13 pour les tests JVM du coeur de messagerie (portee de test uniquement)
* `Dépendance` Ajout de `common-plugin-api.aar` (module AutoJs6 `plugin-api/common-plugin-api`, build hote 6.8.0 / 5281, MPL 2.0) comme contrat de plugin partage, verrouille par hachage dans `locks/host-api-aars.lock`
