package model.network;

import model.Board;
import model.pieces.Piece;
import java.io.*;
import java.net.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Udp extends Thread {
    private boolean spectator;
    private Piece[] allPieces = new Board().getAllPieces();
    private Piece[] sentAllPieces;

    public Udp(boolean spectator) {
        this.spectator = spectator;
    }

    private void callWhiteTurn() {
        if (spectator)
            in();
        else
            out();
    }

    @Override
    public void run() {
        callWhiteTurn();
    }

    // empfängt und deserialisiert allPieces
    private void in() {
        DatagramSocket socket;

        try {
            socket = new DatagramSocket(4711);
        } catch (SocketException e) {
            System.err.println("SocketException: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        DatagramPacket packet;

        while (true) {
            packet = new DatagramPacket(new byte[1400], 1024);

            try {
                socket.receive(packet);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }

            byte data[] = packet.getData();

            try {
                ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(data)));
                allPieces = (Piece[]) ois.readObject();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }

        }
    }

    // sendet und serialisiert allPieces
    private void out() {
        try {
            DatagramPacket packet;
            while (true) {
                InetAddress address = InetAddress.getByName("localhost");

                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                // wegen der Größe von allPieces muss das Objekt noch einmal komprimiert werden
                GZIPOutputStream gzos = new GZIPOutputStream(bos);
                ObjectOutputStream oos = new ObjectOutputStream(gzos);
                oos.writeObject(sentAllPieces);
                oos.close();

                byte[] serializedPieces = bos.toByteArray();

                packet = new DatagramPacket(serializedPieces, serializedPieces.length, address, 4711);
                DatagramSocket socket = new DatagramSocket();
                if (sentAllPieces != null) {
                    socket.send(packet);
                }

                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Piece[] getAllPieces() {
        return allPieces;
    }

    public void setSentAllPieces(Piece[] sentAllPieces) {
        this.sentAllPieces = sentAllPieces;
    }
}