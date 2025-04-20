// filepath: /home/spring/OS/Exercises/rmi-subject/PadImpl.java
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class PadImpl extends UnicastRemoteObject implements Pad {
    
    private Map<String, SRecord> records;
    private String padName;
    
    public PadImpl(String name) throws RemoteException {
        super();
        records = new HashMap<>();
        padName = name;
    }
    
    public void add(SRecord sr) throws RemoteException {
        // Store the serialized SRecord object
        records.put(sr.getName(), sr);
        System.out.println("Added record for: " + sr.getName());
    }
    
    public RRecord consult(String n, boolean forward) throws RemoteException {
        // Look up a record by name
        SRecord sr = records.get(n);
        if (sr != null) {
            return new RRecordImpl(sr);
        } else if (forward) {
            // Forward the request to another pad
            try {
                String otherPad = (padName.equals("Pad1")) ? "Pad2" : "Pad1";
                Pad remotePad = (Pad) Naming.lookup("//localhost/" + otherPad);
                return remotePad.consult(n, false); // Prevent infinite forwarding
            } catch (Exception e) {
                throw new RemoteException("Error forwarding consult", e);
            }
        }
        return null;
    }
    
    // Main method to start the server
    public static void main(String[] args) {
        try {
            // Create and register the RMI registry
            try {
                java.rmi.registry.LocateRegistry.createRegistry(1099);
                System.out.println("RMI registry ready");
            } catch (Exception e) {
                System.out.println("RMI registry already exists");
            }
            
            // Create and register the pads
            PadImpl pad1 = new PadImpl("Pad1");
            Naming.rebind("Pad1", pad1);
            
            PadImpl pad2 = new PadImpl("Pad2");
            Naming.rebind("Pad2", pad2);
            
            System.out.println("Pad servers ready");
        } catch (Exception e) {
            System.out.println("PadImpl error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
