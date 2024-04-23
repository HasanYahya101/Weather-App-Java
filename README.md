## Config Keys:

Create a file named `config.properties` similar to the `example.config.properties` in the root folder of the project. Then insert values similar to this example for both API's. First is for OpenWeatherMap API and Second is `geoapify.com` API Key.

```config.properties
api.key = ahdbhdbhcbdbdainxiaixhidhid88ueed
geolocation.api = cjubduubaujbjdbjbdjbjdbjabjbj
```

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Example Jsons:

### Current Weather:

```json
{
    "coord":{
        "lon":73.0652,
        "lat":33.6938
    },
    "weather":[
        {
            "id":802,
            "main":"Clouds",
            "description":"scattered clouds",
            "icon":"03d"
        }
    ],
    "base":"stations",
    "main":{
        "temp":302.96,
        "feels_like":302.22,
        "temp_min":302.96,
        "temp_max":302.96,
        "pressure":1010,
        "humidity":36
    },
    "visibility":10000,
    "wind":{
        "speed":2.24,
        "deg":205,
        "gust":3.58
    },
    "clouds":{
        "all":45
    },
    "dt":1713863338,
    "sys":{
        "type":2,
        "id":2007435,
        "country":"PK",
        "sunrise":1713832044,
        "sunset":1713879858
    },
    "timezone":18000,
    "id":1176615,
    "name":"Islamabad",
    "cod":200
}
```

## Note:

The Project is completly implemented in `VS Code` using Java Projects.