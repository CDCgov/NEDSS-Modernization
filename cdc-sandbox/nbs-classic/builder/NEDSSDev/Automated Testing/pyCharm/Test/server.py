import socket

HOST = 'localhost'  # Standard loopback interface address (localhost)
PORT = 65432        # Port to listen on (non-privileged ports are > 1023)

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    s.listen()
    conn, addr = s.accept()
    with conn:
        print('Connected by', addr)
        while True:
            c, (client_host, client_port) = s.accept()
            print('Got connection from', client_host, client_port)
            a = 'Server Online\n'
            c.send(a.encode())
            b = 'HTTP/1.0 200 OK\n'
            c.send(b.encode())
            aa = 'Content-Type: text/html\n'
            c.send(aa.encode())
            c.send("""
                <html>
                <body>
                <h1>Hello World</h1> this is my server!
                </body>
                </html>
            """.encode())  # Use triple-quote string.

            c.close()
            data = conn.recv(1024)
            if not data:
                break
            conn.sendall(data)