# IndroLib
Library of methods used for all the Indro dev team plugins
<br>
<p>How to use this library:</p>
-

<h5>Using the SQLConnector and SQLUtils</h5>
```
    import io.github.indroDevTeam.indroLib.datamanager.SQLConnector;
    import io.github.indroDevTeam.indroLib.datamanager.SQLUtils;

    public class Main {
        //Get instance of connector
        private SQLConnector sqlConnector = new SQLConnector(
                "database", //name of database
                "3306", //database port
                "username", //username
                "password", //password
                false, //false = MySQL, true = SQLite
                this //instance of plugin
    );

        //initialize the utils 
        private SQLUtils sqlUtils = new SQLUtils(sqlConnector);
        
        public void onEnable() {
            //create a new table
            sqlUtils.createTable(
                    "first_table", //name if firsts table
                    "id", //primary key (id Column) name
                    "ign VARCHAR(100)", //add another column called ign
                    "kills INT" //add another column called kills
            );
        }
    }
```

<p>With these 2 classes you can easily execute a wide verity of sql commands without having to touch any actual SQL.<br/>You are also able to switch between MySQL and SQLite without having to change any code!</p>

<h5>Using the Home object:</h5>

<h5>Using the Warp object:</h5>

<h5>Using the Rank object and Rank editor:</h5>
