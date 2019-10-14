# Realtime Web

from [WebSockets vs. Server-Sent events/EventSource](https://stackoverflow.com/questions/5195452/websockets-vs-server-sent-events-eventsource) @stackoverflow


## WebSockets vs. Server-Sent events/EventSource

### TLDR summary:

__Advantages of SSE over Websockets:__

- Transported over simple HTTP instead of a custom protocol
- Can be poly-filled with javascript to "backport" SSE to browsers that do not support it yet.
- Built in support for re-connection and event-id
- Simpler protocol
- No trouble with corporate firewalls doing packet inspection

__Advantages of Websockets over SSE:__

- Real time, two directional communication.
- Native support in more browsers
- Ideal use cases of SSE:

__Stock ticker streaming__

- twitter feed updating
- Notifications to browser

__SSE gotchas:__

- No binary support
- Maximum open connections limit


> HTML5Rocks has some good information on SSE. From that page:

### Server-Sent Events vs. WebSockets

Why would you choose Server-Sent Events over WebSockets? Good question.

One reason SSEs have been kept in the shadow is because later APIs like WebSockets provide a richer protocol to perform bi-directional, full-duplex communication. Having a two-way channel is more attractive for things like games, messaging apps, and for cases where you need near real-time updates in both directions. However, in some scenarios data doesn't need to be sent from the client. You simply need updates from some server action. A few examples would be friends' status updates, stock tickers, news feeds, or other automated data push mechanisms (e.g. updating a client-side Web SQL Database or IndexedDB object store). If you'll need to send data to a server, XMLHttpRequest is always a friend.

SSEs are sent over traditional HTTP. That means they do not require a special protocol or server implementation to get working. WebSockets on the other hand, require full-duplex connections and new Web Socket servers to handle the protocol. In addition, Server-Sent Events have a variety of features that WebSockets lack by design such as automatic reconnection, event IDs, and the ability to send arbitrary events.

## References

- [Polling vs SSE vs WebSocket— How to choose the right one](https://codeburst.io/polling-vs-sse-vs-websocket-how-to-choose-the-right-one-1859e4e13bd9)

### Go

- [ทำระบบ Realtime บน Web](https://acoshift.me/2019/0012-realtime-web.html)

### Java

- [ทำ Notification ด้วย Server-Sent Events ใน Spring Boot](http://bit.ly/35yRgYJ)
- [Spring Boot WebFlux + Server-sent events example](https://www.mkyong.com/spring-boot/spring-boot-webflux-server-sent-events-example/)
