/***********************************************************************************************************************

                                                          TCP

# What is TCP?

TCP is a widely used network protocol. It's a "reliable" protocol that runs on top of an unreliable protocol: IP,
short for Internet Protocol. TCP relies on IP to route packets between systems.

# What is IP?

It is the internet postal service. When a program sends data over the network using IP, the data is broken up and
sent as multiple "packets". Each packet contains a header section and a data section. The header contains a source and
destination address. Here the packets are not guaranteed to arrive in order, or even at all. TCP was created to address
these limitations of IP.

# Working of TCP

TCP offers two guarantees: (a) Reliable delivery of packets and (b) In-order delivery of packets.

TCP is a connection-oriented protocol, which means that to interact over TCP a program must first "establish a
connection". To do this, one program takes the role of a "server", and the other program takes the role of a "client".

The server waits for connections, and the client initiates a connection. Once a connection is established, the client &
server can both receive and send data (it's a two-way channel).\

A TCP connection is identified using a unique combination of four values = destination IP address, destination port
number, source IP address and source port number. (Say if your browser opens multiple connections to destination, only
the "source port number" will change, the rest will remain the same).

# TCP HandShake

The TCP handshake is how clients establish connections with servers. This is a 3-step process.

First, the client initiates the connection by sending a SYN (synchronize) packet to the server, indicating a request to
establish a connection. This packet also contains a sequence number to maintain the order of the packets being sent.

The server, upon receiving this SYN packet, sends back a SYN-ACK (synchronize-acknowledge) packet.

In the final step of this three-way handshake, the client acknowledges the server's SYN-ACK packet by sending an ACK
(acknowledge) packet. The connection is considered established once the server receives this last packet.

The three-way handshake ensures a reliable connection is created, verifies both devices are ready for data transmission,
and sets initial sequence numbers for proper ordering of data packets.

***********************************************************************************************************************/