![EHRouteLogoImage](https://i.imgur.com/XCv5rTJ.png)

# EHR Route
#### A secure Electronic Health Records (EHR) storage and sharing solution 

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![codecov](https://codecov.io/gh/MuizMahdi/EHR-Route/branch/master/graph/badge.svg?token=S9E2GZ97ZN)](https://codecov.io/gh/MuizMahdi/EHR-Route)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=MuizMahdi_EHR-Route&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=MuizMahdi_EHR-Route)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=MuizMahdi_EHR-Route&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=MuizMahdi_EHR-Route)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=MuizMahdi_EHR-Route&metric=security_rating)](https://sonarcloud.io/dashboard?id=MuizMahdi_EHR-Route)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=MuizMahdi_EHR-Route&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=MuizMahdi_EHR-Route)
[![Conventional Commits](https://img.shields.io/badge/Conventional%20Commits-1.0.0-yellow.svg)](https://conventionalcommits.org)

##### Note: This repository is a remake of the previous EHRoute server-side implementaiton, it aims to make EHRoute more performant, and cloud native. You can find the old implementation [here](https://github.com/MuizMahdi/EHR-Route-Mono).  

|                   | Status                                                                                                                    | Maintainability                                                                                                                                                                                   | Security                                                                                                                                                                                      |
|-------------------|---------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Auth Service      | [![MuizMahdi](https://circleci.com/gh/MuizMahdi/EHR-Route.svg?style=shield)](https://circleci.com/gh/MuizMahdi/EHR-Route) |                                                                                                                                                                                                   |                                                                                                                                                                                               |
| Account Service   | [![MuizMahdi](https://circleci.com/gh/MuizMahdi/EHR-Route.svg?style=shield)](https://circleci.com/gh/MuizMahdi/EHR-Route) | [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=ehroute%3Aaccount-service&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=ehroute%3Aaccount-service) | [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=ehroute%3Aaccount-service&metric=security_rating)](https://sonarcloud.io/dashboard?id=ehroute%3Aaccount-service) |
| Config Service    | [![MuizMahdi](https://circleci.com/gh/MuizMahdi/EHR-Route.svg?style=shield)](https://circleci.com/gh/MuizMahdi/EHR-Route) |                                                                                                                                                                                                   |                                                                                                                                                                                               |
| Discovery Service | [![MuizMahdi](https://circleci.com/gh/MuizMahdi/EHR-Route.svg?style=shield)](https://circleci.com/gh/MuizMahdi/EHR-Route) |                                                                                                                                                                                                   |                                                                                                                                                                                               |
| Gateway Service   | [![MuizMahdi](https://circleci.com/gh/MuizMahdi/EHR-Route.svg?style=shield)](https://circleci.com/gh/MuizMahdi/EHR-Route) |                                                                                                                                                                                                   |                                                                                                                                                                                               |
| Provider Service  | [![MuizMahdi](https://circleci.com/gh/MuizMahdi/EHR-Route.svg?style=shield)](https://circleci.com/gh/MuizMahdi/EHR-Route) |                                                                                                                                                                                                   |                                                                                                                                                                                               |
| Patient Service   | [![MuizMahdi](https://circleci.com/gh/MuizMahdi/EHR-Route.svg?style=shield)](https://circleci.com/gh/MuizMahdi/EHR-Route) |                                                                                                                                                                                                   |                                                                                                                                                                                               |

# Problem Statement
In 2018 alone, there have been 
[229 healthcare data breaches affecting 6.1M victims]( https://www.beckershospitalreview.com/cybersecurity/6-1m-healthcare-data-breach-victims-in-2018-5-of-the-biggest-breaches-so-far.html).
Such data security concerns are often the main reason that providers are hesitant to share healthcare data. Sharing patient information can help providers reduce readmissions, avoid medication errors, and even decrease duplicate testing.
Genetic studies, cancer/chronic disease registries, substance abuse, population health management, larger-scale analytics, epidemiology/disease tracking, and even interoperability for routine patient care in the emergency department 
[are all potential uses for data sharing](https://healthitsecurity.com/features/benefits-challenges-of-secure-healthcare-data-sharing).

<br/>

# Description
EHR Route is an open source Electronic Health Records (EHR) sharing and storage solution that aims to prevent the most common causes of 
[EHRs data breaches](https://www.healthcareitnews.com/projects/biggest-healthcare-data-breaches-2018-so-far).
It closes gap between healthcare providers and patients, and ensures patients complete privacy and engagement in the process.

It utilizes [Public-key cryptography]( https://en.wikipedia.org/wiki/Public-key_cryptography)
(also known as asymmetric key encryption) And [Blockchain Technology]( https://en.wikipedia.org/wiki/Blockchain) 
using [Proof of authority]( https://en.wikipedia.org/wiki/Proof-of-authority) with a single validator at its core; Public-key cryptography enables Patients maximum engagement by giving them control of choosing who uses, edits, or shares their electronic health record. While blockchain ensures and verifies data integrity.

<br/>

### Use case
Patients receive a public and private key-pair upon registration completion, that gets stored locally on their mobile devices using [EHR Route App](https://github.com/MuizMahdi/EHR-Route-Mobile), whenever a provider needs to access or edit a patient’s EHR, a consent request is sent to the patient, the patient could refuse or accept, if accepted, blocks are signed by the patient and then immediately broadcasted across the network, patients public keys could also be used to verify that they signed the block [Details and UML diagrams will be added to the wiki]. If they didn’t accept the request, then no block is broadcasted.

<br/>

#### The following UML use case diagram demonstrates the <b>basic</b> actions and use cases by healthcare providers and patients.

<br/><br/>

![UML_Diagram](https://dl.dropboxusercontent.com/s/kwjcssks29nzwgy/Provider_Patient_UmlDiagram.png)
