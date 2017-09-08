package reflection;

import utils.DateFormatUtil;

public class RemoteService {
	
	public String echo(String msg)
	{
		return DateFormatUtil.getCurrDateStr() + "Echo-" + msg;
	}

}
