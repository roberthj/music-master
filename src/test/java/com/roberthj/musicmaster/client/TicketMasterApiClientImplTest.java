package com.roberthj.musicmaster.client;

import com.roberthj.musicmaster.models.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TicketMasterApiClientImplTest {

    @Mock
    private HttpWebClient httpWebClient;

    @Captor
    ArgumentCaptor<URI> uriCaptor;


    private TicketMasterApiClientImpl ticketMasterApiClient;

    @BeforeEach
    void setup() {
        ticketMasterApiClient =
                new TicketMasterApiClientImpl(httpWebClient, "testApiKey");
    }

    @Test
    public void verifyThatWebClientIsCalledWithCorrectUri() throws IOException {

        var mockedEventsResponse = getMockedEventResponseJson();

        when(httpWebClient.getSyncronously(any(), any())).thenReturn(mockedEventsResponse);

        ticketMasterApiClient.findEventsForArtist("artistName");

        verify(httpWebClient).getSyncronously(uriCaptor.capture(), any());
        var uriCaptorValue = uriCaptor.getValue().toString();

        assertEquals("http://app.ticketmaster.com/discovery/v2/events.json?keyword=artistName&apikey=testApiKey", uriCaptorValue);

    }

    @Test
    public void verifyThatEventResponseIsMappedCorrectlyToListOfEvents() throws IOException {

        var mockedEventsResponse = getMockedEventResponseJson();

        var expectedEventsList = getMockedListOfEvents();

        when(httpWebClient.getSyncronously(any(), any())).thenReturn(mockedEventsResponse);

        var result = ticketMasterApiClient.findEventsForArtist("artistName");

        assertEquals(expectedEventsList,result);

    }

    private List<Event> getMockedListOfEvents() {

       return List.of(Event.builder()
                .name("WGC Cadillac Championship - Sunday Ticket")
                .type("event")
                .id("vvG1VZKS5pr1qy")
                .url("http://ticketmaster.com/event/0E0050681F51BA4C")
                .startDate("2016-03-06")
                .venue("National Doral")
                .address("4400 NW 87th Avenue")
                .city("Miami")
                .country("United States Of America")
                .build());

    }

    private String getMockedEventResponseJson() {
        return "{\n" +
                "  \"_links\":  {\n" +
                "    \"self\":  {\n" +
                "      \"href\": \"/discovery/v2/events.json?size=1{&page,sort}\",\n" +
                "      \"templated\": true\n" +
                "    },\n" +
                "    \"next\":  {\n" +
                "      \"href\": \"/discovery/v2/events.json?page=1&size=1{&sort}\",\n" +
                "      \"templated\": true\n" +
                "    }\n" +
                "  },\n" +
                "  \"_embedded\":  {\n" +
                "    \"events\":  [\n" +
                "       {\n" +
                "        \"name\": \"WGC Cadillac Championship - Sunday Ticket\",\n" +
                "        \"type\": \"event\",\n" +
                "        \"id\": \"vvG1VZKS5pr1qy\",\n" +
                "        \"test\": false,\n" +
                "        \"url\": \"http://ticketmaster.com/event/0E0050681F51BA4C\",\n" +
                "        \"locale\": \"en-us\",\n" +
                "        \"images\":  [\n" +
                "           {\n" +
                "            \"ratio\": \"16_9\",\n" +
                "            \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_RETINA_LANDSCAPE_16_9.jpg\",\n" +
                "            \"width\": 1136,\n" +
                "            \"height\": 639,\n" +
                "            \"fallback\": false\n" +
                "          },\n" +
                "           {\n" +
                "            \"ratio\": \"3_2\",\n" +
                "            \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_RETINA_PORTRAIT_3_2.jpg\",\n" +
                "            \"width\": 640,\n" +
                "            \"height\": 427,\n" +
                "            \"fallback\": false\n" +
                "          },\n" +
                "           {\n" +
                "            \"ratio\": \"16_9\",\n" +
                "            \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_TABLET_LANDSCAPE_LARGE_16_9.jpg\",\n" +
                "            \"width\": 2048,\n" +
                "            \"height\": 1152,\n" +
                "            \"fallback\": false\n" +
                "          },\n" +
                "           {\n" +
                "            \"ratio\": \"16_9\",\n" +
                "            \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_TABLET_LANDSCAPE_16_9.jpg\",\n" +
                "            \"width\": 1024,\n" +
                "            \"height\": 576,\n" +
                "            \"fallback\": false\n" +
                "          },\n" +
                "           {\n" +
                "            \"ratio\": \"16_9\",\n" +
                "            \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_EVENT_DETAIL_PAGE_16_9.jpg\",\n" +
                "            \"width\": 205,\n" +
                "            \"height\": 115,\n" +
                "            \"fallback\": false\n" +
                "          },\n" +
                "           {\n" +
                "            \"ratio\": \"3_2\",\n" +
                "            \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_ARTIST_PAGE_3_2.jpg\",\n" +
                "            \"width\": 305,\n" +
                "            \"height\": 203,\n" +
                "            \"fallback\": false\n" +
                "          },\n" +
                "           {\n" +
                "            \"ratio\": \"16_9\",\n" +
                "            \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_RETINA_PORTRAIT_16_9.jpg\",\n" +
                "            \"width\": 640,\n" +
                "            \"height\": 360,\n" +
                "            \"fallback\": false\n" +
                "          },\n" +
                "           {\n" +
                "            \"ratio\": \"4_3\",\n" +
                "            \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_CUSTOM.jpg\",\n" +
                "            \"width\": 305,\n" +
                "            \"height\": 225,\n" +
                "            \"fallback\": false\n" +
                "          },\n" +
                "           {\n" +
                "            \"ratio\": \"16_9\",\n" +
                "            \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_RECOMENDATION_16_9.jpg\",\n" +
                "            \"width\": 100,\n" +
                "            \"height\": 56,\n" +
                "            \"fallback\": false\n" +
                "          },\n" +
                "           {\n" +
                "            \"ratio\": \"3_2\",\n" +
                "            \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_TABLET_LANDSCAPE_3_2.jpg\",\n" +
                "            \"width\": 1024,\n" +
                "            \"height\": 683,\n" +
                "            \"fallback\": false\n" +
                "          }\n" +
                "        ],\n" +
                "        \"sales\":  {\n" +
                "          \"public\":  {\n" +
                "            \"startDateTime\": \"2015-10-02T11:00:00Z\",\n" +
                "            \"startTBD\": false,\n" +
                "            \"endDateTime\": \"2016-03-06T23:00:00Z\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"dates\":  {\n" +
                "          \"start\":  {\n" +
                "            \"localDate\": \"2016-03-06\",\n" +
                "            \"dateTBD\": false,\n" +
                "            \"dateTBA\": false,\n" +
                "            \"timeTBA\": true,\n" +
                "            \"noSpecificTime\": false\n" +
                "          },\n" +
                "          \"timezone\": \"America/New_York\",\n" +
                "          \"status\":  {\n" +
                "            \"code\": \"offsale\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"classifications\":  [\n" +
                "           {\n" +
                "            \"primary\": true,\n" +
                "            \"segment\":  {\n" +
                "              \"id\": \"KZFzniwnSyZfZ7v7nE\",\n" +
                "              \"name\": \"Sports\"\n" +
                "            },\n" +
                "            \"genre\":  {\n" +
                "              \"id\": \"KnvZfZ7vAdt\",\n" +
                "              \"name\": \"Golf\"\n" +
                "            },\n" +
                "            \"subGenre\":  {\n" +
                "              \"id\": \"KZazBEonSMnZfZ7vFI7\",\n" +
                "              \"name\": \"PGA Tour\"\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"promoter\":  {\n" +
                "          \"id\": \"682\"\n" +
                "        },\n" +
                "        \"_links\":  {\n" +
                "          \"self\":  {\n" +
                "            \"href\": \"/discovery/v2/events/vvG1VZKS5pr1qy?locale=en-us\"\n" +
                "          },\n" +
                "          \"attractions\":  [\n" +
                "             {\n" +
                "              \"href\": \"/discovery/v2/attractions/K8vZ917uc57?locale=en-us\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"venues\":  [\n" +
                "             {\n" +
                "              \"href\": \"/discovery/v2/venues/KovZpZAaEldA?locale=en-us\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        \"_embedded\":  {\n" +
                "          \"venues\":  [\n" +
                "             {\n" +
                "              \"name\": \"National Doral\",\n" +
                "              \"type\": \"venue\",\n" +
                "              \"id\": \"KovZpZAaEldA\",\n" +
                "              \"test\": false,\n" +
                "              \"locale\": \"en-us\",\n" +
                "              \"postalCode\": \"33178\",\n" +
                "              \"timezone\": \"America/New_York\",\n" +
                "              \"city\":  {\n" +
                "                \"name\": \"Miami\"\n" +
                "              },\n" +
                "              \"state\":  {\n" +
                "                \"name\": \"Florida\",\n" +
                "                \"stateCode\": \"FL\"\n" +
                "              },\n" +
                "              \"country\":  {\n" +
                "                \"name\": \"United States Of America\",\n" +
                "                \"countryCode\": \"US\"\n" +
                "              },\n" +
                "              \"address\":  {\n" +
                "                \"line1\": \"4400 NW 87th Avenue\"\n" +
                "              },\n" +
                "              \"location\":  {\n" +
                "                \"longitude\": \"-80.33854298\",\n" +
                "                \"latitude\": \"25.81260379\"\n" +
                "              },\n" +
                "              \"markets\":  [\n" +
                "                 {\n" +
                "                  \"id\": \"15\"\n" +
                "                }\n" +
                "              ],\n" +
                "              \"_links\":  {\n" +
                "                \"self\":  {\n" +
                "                  \"href\": \"/discovery/v2/venues/KovZpZAaEldA?locale=en-us\"\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          ],\n" +
                "          \"attractions\":  [\n" +
                "             {\n" +
                "              \"name\": \"Cadillac Championship\",\n" +
                "              \"type\": \"attraction\",\n" +
                "              \"id\": \"K8vZ917uc57\",\n" +
                "              \"test\": false,\n" +
                "              \"locale\": \"en-us\",\n" +
                "              \"images\":  [\n" +
                "                 {\n" +
                "                  \"ratio\": \"16_9\",\n" +
                "                  \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_RETINA_LANDSCAPE_16_9.jpg\",\n" +
                "                  \"width\": 1136,\n" +
                "                  \"height\": 639,\n" +
                "                  \"fallback\": false\n" +
                "                },\n" +
                "                 {\n" +
                "                  \"ratio\": \"3_2\",\n" +
                "                  \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_RETINA_PORTRAIT_3_2.jpg\",\n" +
                "                  \"width\": 640,\n" +
                "                  \"height\": 427,\n" +
                "                  \"fallback\": false\n" +
                "                },\n" +
                "                 {\n" +
                "                  \"ratio\": \"16_9\",\n" +
                "                  \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_TABLET_LANDSCAPE_LARGE_16_9.jpg\",\n" +
                "                  \"width\": 2048,\n" +
                "                  \"height\": 1152,\n" +
                "                  \"fallback\": false\n" +
                "                },\n" +
                "                 {\n" +
                "                  \"ratio\": \"16_9\",\n" +
                "                  \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_TABLET_LANDSCAPE_16_9.jpg\",\n" +
                "                  \"width\": 1024,\n" +
                "                  \"height\": 576,\n" +
                "                  \"fallback\": false\n" +
                "                },\n" +
                "                 {\n" +
                "                  \"ratio\": \"16_9\",\n" +
                "                  \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_EVENT_DETAIL_PAGE_16_9.jpg\",\n" +
                "                  \"width\": 205,\n" +
                "                  \"height\": 115,\n" +
                "                  \"fallback\": false\n" +
                "                },\n" +
                "                 {\n" +
                "                  \"ratio\": \"3_2\",\n" +
                "                  \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_ARTIST_PAGE_3_2.jpg\",\n" +
                "                  \"width\": 305,\n" +
                "                  \"height\": 203,\n" +
                "                  \"fallback\": false\n" +
                "                },\n" +
                "                 {\n" +
                "                  \"ratio\": \"16_9\",\n" +
                "                  \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_RETINA_PORTRAIT_16_9.jpg\",\n" +
                "                  \"width\": 640,\n" +
                "                  \"height\": 360,\n" +
                "                  \"fallback\": false\n" +
                "                },\n" +
                "                 {\n" +
                "                  \"ratio\": \"4_3\",\n" +
                "                  \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_CUSTOM.jpg\",\n" +
                "                  \"width\": 305,\n" +
                "                  \"height\": 225,\n" +
                "                  \"fallback\": false\n" +
                "                },\n" +
                "                 {\n" +
                "                  \"ratio\": \"16_9\",\n" +
                "                  \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_RECOMENDATION_16_9.jpg\",\n" +
                "                  \"width\": 100,\n" +
                "                  \"height\": 56,\n" +
                "                  \"fallback\": false\n" +
                "                },\n" +
                "                 {\n" +
                "                  \"ratio\": \"3_2\",\n" +
                "                  \"url\": \"http://s1.ticketm.net/dam/a/196/6095e742-64d1-4b15-aeac-c9733c52d196_66341_TABLET_LANDSCAPE_3_2.jpg\",\n" +
                "                  \"width\": 1024,\n" +
                "                  \"height\": 683,\n" +
                "                  \"fallback\": false\n" +
                "                }\n" +
                "              ],\n" +
                "              \"classifications\":  [\n" +
                "                 {\n" +
                "                  \"primary\": true,\n" +
                "                  \"segment\":  {\n" +
                "                    \"id\": \"KZFzniwnSyZfZ7v7nE\",\n" +
                "                    \"name\": \"Sports\"\n" +
                "                  },\n" +
                "                  \"genre\":  {\n" +
                "                    \"id\": \"KnvZfZ7vAdt\",\n" +
                "                    \"name\": \"Golf\"\n" +
                "                  },\n" +
                "                  \"subGenre\":  {\n" +
                "                    \"id\": \"KZazBEonSMnZfZ7vFI7\",\n" +
                "                    \"name\": \"PGA Tour\"\n" +
                "                  }\n" +
                "                }\n" +
                "              ],\n" +
                "              \"_links\":  {\n" +
                "                \"self\":  {\n" +
                "                  \"href\": \"/discovery/v2/attractions/K8vZ917uc57?locale=en-us\"\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"page\":  {\n" +
                "    \"size\": 1,\n" +
                "    \"totalElements\": 87958,\n" +
                "    \"totalPages\": 87958,\n" +
                "    \"number\": 0\n" +
                "  }\n" +
                "}";
    }

}
