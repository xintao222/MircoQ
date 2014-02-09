/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/javafx/workspace/MicSay/src/com/unic/micsay/services/MessageBinder.aidl
 */
package com.unic.micsay.services;
public interface MessageBinder extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.unic.micsay.services.MessageBinder
{
private static final java.lang.String DESCRIPTOR = "com.unic.micsay.services.MessageBinder";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.unic.micsay.services.MessageBinder interface,
 * generating a proxy if needed.
 */
public static com.unic.micsay.services.MessageBinder asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.unic.micsay.services.MessageBinder))) {
return ((com.unic.micsay.services.MessageBinder)iin);
}
return new com.unic.micsay.services.MessageBinder.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_currentAccount:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.currentAccount();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_startChat:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.startChat(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_send:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.send(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_closeChat:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.closeChat(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.unic.micsay.services.MessageBinder
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public java.lang.String currentAccount() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_currentAccount, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void startChat(java.lang.String email) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(email);
mRemote.transact(Stub.TRANSACTION_startChat, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void send(java.lang.String message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(message);
mRemote.transact(Stub.TRANSACTION_send, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void closeChat(java.lang.String email) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(email);
mRemote.transact(Stub.TRANSACTION_closeChat, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_currentAccount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_startChat = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_send = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_closeChat = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public java.lang.String currentAccount() throws android.os.RemoteException;
public void startChat(java.lang.String email) throws android.os.RemoteException;
public void send(java.lang.String message) throws android.os.RemoteException;
public void closeChat(java.lang.String email) throws android.os.RemoteException;
}
