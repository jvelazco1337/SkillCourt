
/*
 * File:   connection.c
 */
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>





void error(char *msg)
{
    perror(msg);
    exit(1);
}

void sendData( int sockfd, int a ) {
  int n;

  char buffer[32];
  sprintf( buffer, "%d\n", a );
  if ( (n = write( sockfd, buffer, strlen(buffer) ) ) < 0 ){
    error(( "ERROR writing to socket") );
  }
  buffer[n] = '\0';
}

int getData( int sockfd ) {
  char buffer[32];
  int n;

  if ( (n = read(sockfd,buffer,31) ) < 0 ){
    error( ( "ERROR reading from socket") );
  }
  buffer[n] = '\0';
  return atoi( buffer );
}

int main(int argc, char *argv[]) {
     int sockfd, newsockfd, portno = 23 , clilen;
     char buffer[256];
     struct sockaddr_in serv_addr, cli_addr;
     int n;
     int data;

     printf( "using port #%d\n", portno );

     sockfd = socket(AF_INET, SOCK_STREAM, 0);

     if (sockfd < 0){
         error( ("ERROR opening socket") );
     }
     bzero((char *) &serv_addr, sizeof(serv_addr));

     serv_addr.sin_family = AF_INET;
     serv_addr.sin_addr.s_addr = INADDR_ANY;
     serv_addr.sin_port = htons( portno );

     if (bind(sockfd, (struct sockaddr *) &serv_addr,
              sizeof(serv_addr)) < 0){
       error( const_cast<char *>( "ERROR on binding" ) );
     }
     listen(sockfd,5);
     clilen = sizeof(cli_addr);

     //Waiting for connection
     while ( 1 ) {
        printf( "Waiting for client\n" );
        newsockfd = accept( sockfd,(struct sockaddr *)
                &cli_addr, (socklen_t*) &clilen);
        if(  newsockfd  < 0 ){
            error( "ERROR on accept" );
        }
        printf( "Connecting with client\n" );

        //Waiting for number from app
        data = getData( newsockfd );
        printf( "got %d\n", data ); //May not be necessary only for testing purposes
        if ( data < 0 )
           break;
        //Receives a 0 for LED to illuminate green
        if ( data == '0'){
            //Turn all LEDs off
            //Turn Green LED on
        }
        //Receives a 1 for LED to illuminate red
        if ( data == '1'){
            //Turn all LEDs off
            //Turn RED LED on
        }

        //Close communication when a -2 is sent
        if ( data == -2 )
          close( newsockfd );
     }
     //lets try again
     return 0;
}


