# solichmag
Это веб-приложение площадки по продаже автомобилей

## Календарное планирование
| Этапы                                       | Дата начала | Дата завершения |
|:--------------------------------------------|:-----------:|:---------------:|
| Проектирование и разработка сервиса         | 15.01.2024  |   12.02.2024    |
| Архитектура                                 | 13.02.2024  |   26.02.2024    |
| База данных                                 | 27.02.2024  |   12.03.2024    |
| Функциональный прототип                     | 14.03.2024  |   01.04.2024    |

## База знаний
- [Требования]([https://docs.google.com/spreadsheets/d/1ar7k1M1Ny8dvJw3D6-R4sVilpewrDADMEtiyr-7OKU8/edit#gid=0])


## Структура проекта

### src/main/java/com/example/solichmag/

- configurations/
    - MvcConfig.java
    - SecurityConfig.java
- controllers/
    - AdminController.java
    - ImageController.java
    - ProductController.java
    - UserController.java
- models/
    - enums/
        - Role.java
    - Image.java
    - Product.java
    - User.java
- repositories/
    - ImageRepository.java
    - ProductRepository.java
    - UserRepository.java
- services/
    - CustomUserDetailsService.java
    - ProductService.java
    - UserService.java
- SolichmagApplication.java

### src/main/resources/

- static/
    - css/
        - style.css
    - images/
        - avatar.png
- templates/
    - blocks/
        - admin.ftlh
        - login.ftlh
        - my-products.ftlh
        - product-info.ftlh
        - products.ftlh
        - profile.ftlh
        - registration.ftlh
        - user-edit.ftlh
        - user-info.ftlh
- application.properties

### src/test/java/com/example/solichmag/

- SolichmagApplicationTests.java


