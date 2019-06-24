## Installation

You must install [Node.js](https://nodejs.org/) to run.

Install the dependencies and devDependencies.

```sh
$ npm i --prefix ./client
```
or use the install script in the scripts folder.

| OS | Script name |
| ------ | ------ |
| Windows | `install.bat` |
| Linux | `install.sh` |

## Run client

```sh
$ cd client/
$ npm start
```
or use the run script in the scripts folder.

| OS | Script name |
| ------ | ------ |
| Windows | `start.bat` |
| Linux | `start.sh` |


## Build and Deploy to Apache

You must install [Apache](https://httpd.apache.org/download.cgi).
Download for Linux:
###### ArchLinux: ```sudo pacman -S apache```
###### Ubuntu: ```sudo apt-get install apache2```


In the `/gui/client/src/config.json` file, change the `host` and `port` values ​​to server values


#####For Linux:
You must install: `a2enmod` and run `sudo a2enmod rewrite`




#### Build client

```sh
$ cd client/
$ npm run build
```
or use the run script in the scripts folder.

| OS | Script name |
| ------ | ------ |
| Windows | `build.bat` |
| Linux | `build.sh` |

the assembled project is located along the way:
```sh
/gui/client/build
```

#### Apache setup

Open up the apache configuration file: `httpd.conf`

Comment out the `unique_id_modul` and `rewrite_module`
```
#LoadModule unique_id_module modules/mod_unique_id.so
#LoadModule rewrite_module modules/mod_rewrite.so
```

and add lines
```
<VirtualHost *:80>
  <Directory "/">
    RewriteEngine on
    # Don't rewrite files or directories
    RewriteCond %{REQUEST_FILENAME} -f [OR]
    RewriteCond %{REQUEST_FILENAME} -d
    RewriteRule ^ - [L]
    # Rewrite everything else to index.html to allow html5 state links
    RewriteRule ^ index.html [L]
  </Directory>
</VirtualHost>
```

#### Copy the collected project to apache

In the `httpd.conf` file, look for the `DocumentRoot "${path}"` line and see the specified path.

Copy all the files from `/gui/client/build` to `${path}`, which was listed in `DocumentRoot`.

#### Run Apache server

##### Windows: 
run `httpd.exe` from the `bin` folder
##### Linux
run command:

######ArchLinux: ```sudo systemctl start httpd```
######Ubuntu: ```sudo service apache2 start```

Open [localhost](https://localhost:80) in the browser and enjoy the result.











