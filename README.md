# Music Master

Service that connects to Spotifys and Ticketmasters Apis to present interesting events for artists and related artists

## Endpoints

## Find events
Endpoint: http://localhost:8080/v1/find_events/<artist name>
Method: get

Response: A list of events for the provided artist and for a number of related (as defined by the Spotify Api)

If there are multiple artists with the same name the response will be for the most popular one of them.


## How to run locally

### Generate Api keys for Spotify and Ticketmaster

Spotify: 
 * Go to https://developer.spotify.com/ 
 * Create account and/or sign in
 * Go to https://developer.spotify.com/dashboard
 * Create app and your `ClientId` and `Client secret` will be generated

Ticketmaster: 
 * Go to https://developer.ticketmaster.com/
 * Create account and/or sign in
 * Go to `My Apps` and `ADD A NEW APP`
 * Your `Api key` will be generated


### Set environment variables with the generated values

* For IntelliJ:
    * Go to `Run/Edit Configurations` folder
    * Add new Application
    * Select `Java 17`
    * Choose `com.roberthj.musicmaster.Application` as main class
    * Add `SPOTIFY_API_CLIENT_ID` environment variable 
    * Add `SPOTIFY_API_CLIENT_SECRET` environment variable
    * Add `TICKETMASTER_API_KEY` environment variable 

The variable values will now be picked up by the service from the properties file `application.yaml`

Run the service på clicking run for your run configuration

### Run the local postgres database


