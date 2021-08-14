[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="https://www.taximobility.com/blog/wp-content/uploads/2018/07/Bus-Charter-Management-Software.png" alt="Logo">
  </a>

  <h3 align="center">BUS LINES MANAGEMENT</h3>

  <p align="center">
    <a href="#"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="#">View Demo</a>
    ·
    <a href="#">Report Bug</a>
    ·
    <a href="#">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

![Product Name Screen Shot](https://user-images.githubusercontent.com/55595623/117468868-52cff580-af7f-11eb-9db8-ea0f64c5910f.png)

Bus Line Management is an app that will help many transportation companies to Manage activities, Calculate income, Sell ticket and Manage Staff, Bus, Route. 

Benefits:
* Incredibly beautiful & simple UI.
* Easy to use.
* High performance.
* Save time & Human Resources reducing.

### Built With

This app is being developed by a group of student, currently studying at Danang University of Technology. We're using these technologies to develope the app:
* [Java](https://www.java.com/en/)
* [JavaFX](https://openjfx.io/)
* [Hibernate](https://hibernate.org/)



<!-- GETTING STARTED -->
## Getting Started

This is a list of software that you need to install to optimize the code in your way:
  1. Any IDE that supports Java (InteliJ Ultimate, Eclipse, NetBeans, ...)
  2. Scene Builder
  3. Microsoft SQL Server 2019
  

### Prerequisites

You still need to install these things first:
* Java SE Development Kit


### Installation

1. Copy the address of the project
2. Clone the repo
   ```sh
   git clone https://github.com/dungngminh/QuanLyNhaXe_Maven
   ```
3. Install Maven packages
   ```
   find the maven tag in your IDE, click install and that'll run automatically
   ```
4. Run the database script
   ```sh
    database-init.sh
   ```
   
### Directory Structure

```
project
│   README.md
│
└───screenshot
│
│
└───src/main
│   └───java
|   |   |   main.java                           <- [Main class in java Program]
|   |   |
│   │   │   Controller                          <- [Controller]
|   |   |   └───Admin Controller
|   |   |   └───BusType Controller
|   |   |   └───TicketSeller Controller
|   |   |
│   │   │   Model                               <- [Model]
|   |   |   └───Entity Class
|   |   |   └───ViewModel Class
|   |   |
|   |   |   Services                            <- [Services]
|   |   |   └───DBConnectionHelper Class
|   |   |   └───BusinessLogic Class
|   |   |
|   |   |   Utils                               <- [Utilities]
|   |   |   └───HibernateUtil Class
│   │
│   └───resources                 
│   |    │   css
|   |    |
│   |    │   images
|   |    |
|   |    |   util
|   |    |
|   |    |   views                              <- [Contains FXML file for UI/ UX]
|   |    |   └───Admin View
|   |    |   └───BusType View
|   |    |   └───Seller View
```


<!-- USAGE EXAMPLES -->
## Usage

There are some screenshots about this app, and the sequence of using the app.

**1. Login**
    <br/>
    <br/>
    ![image_login](https://github.com/dungngminh/QuanLyNhaXe_Maven/blob/feature/screenshot/login1.jpg)
    
**2. Admin Dashboard**
    <br/>
    <br/>
    ![image_dashboard](https://raw.githubusercontent.com/dungngminh/QuanLyNhaXe_Maven/feature/screenshot/dashboard.png)
    
**3. Main Page**
    <br/>
    <br/>
    ![image_main](https://github.com/dungngminh/QuanLyNhaXe_Maven/blob/feature/screenshot/ticketseller_dashboard1.jpg)

**4. Bus Page**
    <br/>
    <br/>
    ![image_bus1](https://github.com/dungngminh/QuanLyNhaXe_Maven/blob/feature/screenshot/bus.jpg)
    <br/>
    <br/>
    ![imgae_bus2](https://github.com/dungngminh/QuanLyNhaXe_Maven/blob/feature/screenshot/bus1.jpg)

**5. Route**
    <br/>
    <br/>
    ![image_route1](https://github.com/dungngminh/QuanLyNhaXe_Maven/blob/feature/screenshot/route1.jpg)
    <br/>
    <br/>
    ![image_route2](https://github.com/dungngminh/QuanLyNhaXe_Maven/blob/feature/screenshot/route2.jpg)
    
**6. Route List**
    <br/>
    <br/>
    ![image_ticket2](https://github.com/dungngminh/QuanLyNhaXe_Maven/blob/feature/screenshot/routebydate.jpg)
    
**7. Driver**
    <br/>
    <br/>
    ![image_driver1](https://github.com/dungngminh/QuanLyNhaXe_Maven/blob/feature/screenshot/driver1.jpg)
     <br/>
     <br/>
    ![image_driver2](https://github.com/dungngminh/QuanLyNhaXe_Maven/blob/feature/screenshot/driver2.jpg)

**8. Decentralize**
    <br/>
    <br/>
    ![image_acc1](https://github.com/dungngminh/QuanLyNhaXe_Maven/blob/feature/screenshot/acc1.jpg)
    <br/>
    <br/>
    ![image_acc2](https://github.com/dungngminh/QuanLyNhaXe_Maven/blob/feature/screenshot/acc2.jpg)
    
**8. Ticket Detail**
    <br/>
    <br/>
    ![image_ticket1](https://github.com/dungngminh/QuanLyNhaXe_Maven/blob/feature/screenshot/ticketdetail.jpg)


    
    
    

<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/dungngminh/QuanLyNhaXe_Maven/issues) for a list of proposed features (and known issues).



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

DUT Team (GitFake company) - [Facebook](https://facebook.com/thinhquocle1002) - dut.teamwork@gmail.com

Project Link: [Bus Lines Management](https://github.com/dungngminh/QuanLyNhaXe_Maven)



<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements
* [StackoverFlow](https://www.webpagefx.com/tools/emoji-cheat-sheet)
* [Java Docs](https://shields.io)
* [JDBC Docs](https://choosealicense.com)
* [Asynchronous booking ticket](https://pages.github.com)
* [CSS in JavaFX](https://daneden.github.io/animate.css)
* [Loaders.css](https://connoratherton.com/loaders)
* [JFoenix](https://kenwheeler.github.io/slick)
* [Hibernate docs](https://github.com/cferdinandi/smooth-scroll)
* [Encrypt SHA-1](http://jvectormap.com)
* [Font Awesome](https://fontawesome.com)





<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/badge/CONTRIBUTORS-_4_-brightgreen?style=for-the-badge
[contributors-url]: https://github.com/dungngminh/QuanLyNhaXe_Maven/graphs/contributors
[forks-shield]: https://img.shields.io/badge/FORKS-_1_-blue?style=for-the-badge
[forks-url]: https://github.com/dungngminh/QuanLyNhaXe_Maven/network/members
[stars-shield]: https://img.shields.io/badge/STARS-_3_-blue?style=for-the-badge
[stars-url]: https://github.com/dungngminh/QuanLyNhaXe_Maven/stargazers
[issues-shield]: https://img.shields.io/badge/ISSUES-_2%20closed_-blue?style=for-the-badge
[issues-url]: https://github.com/dungngminh/QuanLyNhaXe_Maven/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/dungngminh/QuanLyNhaXe_Maven/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/le-quoc-thinh-307b47186/
[product-screenshot]: images/screenshot.png
