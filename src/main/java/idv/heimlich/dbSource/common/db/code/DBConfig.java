package idv.heimlich.dbSource.common.db.code;

import idv.heimlich.dbSource.common.db.AbstractDBSessionManager;
import idv.heimlich.dbSource.common.db.IDBSession;
import idv.heimlich.dbSource.common.db.DBSessionFTZBManager;
import idv.heimlich.dbSource.common.db.DBSessionManager;


public enum DBConfig {
	
	PFTZBPool {
		@Override
		public AbstractDBSessionManager getDBSessionManager() {
			return new DBSessionFTZBManager();
		}
		
	},
	PCLMSPool {
		@Override
		public AbstractDBSessionManager getDBSessionManager() {
			return new DBSessionManager();
		}
	
	},

	;
	final String connid;

	private DBConfig() {
		this.connid = name();
	}

	 public abstract AbstractDBSessionManager getDBSessionManager();
	 
	 public IDBSession getXdaoSession(){
		 return this.getDBSessionManager().getDBSession();
	 }

}
