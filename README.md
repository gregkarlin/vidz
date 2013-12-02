# Route Test #

Corresponds to the guide at [http://scalatra.org/2.2/guides/persistence/slick.html](http://scalatra.org/2.2/guides/persistence/slick.html)

## Build & Run ##

```sh
$ git clone https://github.com/scalatra/scalatra-website-examples.git
$ cd scalatra-website-examples/2.2/persistence/scalatra-slick
$ chmod +x sbt
$ ./sbt
> container:start
```

## Initialize some Data Bullshit (This only needs to be done once since db is saved to disk) ##

Visit the following urls:
http://localhost:8080/db/create-tables
http://localhost:8080/db/load-data

I will add an initialize script when I feel like it.

Now open the site's [root page](http://localhost:8080/) in your browser.
