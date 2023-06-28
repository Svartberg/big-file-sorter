# ТЕСТОВОЕ ЗАДАНИЕ

Реализовать сервис / метод позволяющий отсортировать справочник, большой текстовый файл, где с каждой новой строки есть термин, разделитель и определение.

Ограничения: **файл изначально больше, чем может считать программа**.

Для реализованного сервиса / метода, необходимо подготовить документацию и покрыть код тестами.


## API

Для описания и документирования был использован **Swagger**. Подробнее о том, как можно обратиться к Swagger UI для изучения API проекта, можно узнать в блоке **Запуск и тестирование приложения**.

Проектирование **веб-API RESTFUL** может происходить по-разному.

Поэтому, было принято решение использовать за основу рекомендации из документации Microsoft: [Проектирование веб-API RESTFUL](https://docs.microsoft.com/ru-ru/azure/architecture/best-practices/api-design).

В документации подробно описываются проблемы, которые следует учитывать при разработке веб-API, а так-же варианты их решения.

Сервис использует один контроллер **SorterController** для взаимодействия с сервисом.

## Запуск и тестирование приложения

1. Запустить проект
```
mvn spring-boot:run
```

2. Перейти http://localhost:8090/swagger-ui/ для ознакомления с API проекта

```yml
server:
  port: 8090
```

### Примеры запросов

Для примера работы, в корне проекта лежат два файла: **big_file_test** и **big_file_test2**, которые можно использовать для сортировки.

**1. Создание новой записи в очереди для сортировки с указанием пути к сортеруемому файлу:**
```thymeleafurlexpressions
curl --location --request POST 'http://localhost:8090/api/v1/sorter/file' \
--header 'Content-Type: application/json' \
--data-raw '{
    "path":"big_file_test"
}'
```
**2. Получение данных о статусе сортируемого файла:**
```thymeleafurlexpressions
curl --location --request GET 'http://localhost:8090/api/v1/sorter/file?id=1' 
```
