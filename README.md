# Dynamic Data Lists REST Client

This project is a Liferay plugin that creates an API for retrieving Dynamic Data Lists records.

Create a new Data Definition:

![Create a Data Definition](/images/data-definition.png)

Create a Dynamic Data List using this data definition:

![Create a DDL](/images/create-ddl.png)

Add some records to the list:

![Add some data](/images/add-some-records.png)

Get the id of the DDL:

![Get the ID](/images/get-id-of-ddl.png)

Perform a GET to the service passing the id as parameter, example: http://localhost:8080/o/dynamic-data-list-client/ddlclient/get-data-by-record-set-id/${id-of-the-ddl}, replacing *${id-of-the-ddl}* for the DDL id. The result should be like the following:

```
[
    {
        "availableLanguageIds": [
            "en_US"
        ],
        "defaultLanguageId": "en_US",
        "fieldValues": [
            {
                "instanceId": "lwxk",
                "name": "code",
                "value": {
                    "en_US": "ACC"
                }
            },
            {
                "instanceId": "tfes",
                "name": "profession",
                "value": {
                    "en_US": "Accountant"
                }
            }
        ]
    },
    {
        "availableLanguageIds": [
            "en_US"
        ],
        "defaultLanguageId": "en_US",
        "fieldValues": [
            {
                "instanceId": "wdgy",
                "name": "code",
                "value": {
                    "en_US": "BB"
                }
            },
            {
                "instanceId": "gsru",
                "name": "profession",
                "value": {
                    "en_US": "Barber"
                }
            }
        ]
    },
...
```

You can use [JsonPath](https://github.com/json-path/JsonPath/) to get the itens of the list and use as a data provider for your forms, for example:

```
$..fieldValues[?(@.name == 'profession')].value.en_US
```

Should retrive the 'profession' field of the list:

```

```

