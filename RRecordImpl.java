import java.rmi.*;
import java.rmi.server.*;

public class RRecordImpl extends UnicastRemoteObject implements RRecord {
    
    private SRecord record;
    
    public RRecordImpl(SRecord record) throws RemoteException {
        super();
        this.record = record;
    }
    
    @Override
    public String getName() throws RemoteException {
        return record.getName();
    }
    
    @Override
    public String getEmail() throws RemoteException {
        return record.getEmail();
    }
}
