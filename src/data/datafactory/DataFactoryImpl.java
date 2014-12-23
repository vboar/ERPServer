/**
 * 抽象工厂实现
 * @author Vboar
 * @date 2014/10/26
 */

package data.datafactory;

import data.accountdata.AccountDataServiceImpl;
import data.commoditydata.CategoryDataServiceImpl;
import data.commoditydata.CommodityDataServiceImpl;
import data.customerdata.CustomerDataServiceImpl;
import data.exceptiondata.ExceptionDataServiceImpl;
import data.exceptiondata.WarningDataServiceImpl;
import data.initialdata.InitialDataServiceImpl;
import data.logdata.LogDataServiceImpl;
import data.messagedata.MessageDataServiceImpl;
import data.paymentdata.CashDataServiceImpl;
import data.paymentdata.PaymentDataServiceImpl;
import data.presentdata.PresentDataServiceImpl;
import data.promotiondata.CustomerGiftDataserviceImpl;
import data.promotiondata.SpecialOfferDataServiceImpl;
import data.promotiondata.TotalGiftDataServiceImpl;
import data.purchasedata.PurchaseDataServiceImpl;
import data.saledata.SaleDataServiceImpl;
import data.stockdata.StockDataServiceImpl;
import data.system.SystemDataServiceImpl;
import data.userdata.UserDataServiceImpl;
import dataservice.accountdataservice.AccountDataService;
import dataservice.commoditydataservice.CategoryDataService;
import dataservice.commoditydataservice.CommodityDataService;
import dataservice.customerdataservice.CustomerDataService;
import dataservice.datafactoryservice.DataFactory;
import dataservice.exceptiondataservice.ExceptionDataService;
import dataservice.exceptiondataservice.WarningDataService;
import dataservice.initialdataservice.InitialDataService;
import dataservice.logdataservice.LogDataService;
import dataservice.messagedataservice.MessageDataService;
import dataservice.paymentdataservice.CashDataService;
import dataservice.paymentdataservice.PaymentDataService;
import dataservice.presentdataservice.PresentDataService;
import dataservice.promotiondataservice.CustomerGiftDataService;
import dataservice.promotiondataservice.SpecialOfferDataService;
import dataservice.promotiondataservice.TotalGiftDataService;
import dataservice.purchasedataservice.PurchaseDataService;
import dataservice.saledataservice.SaleDataService;
import dataservice.stockdataservice.StockDataService;
import dataservice.systemdateservice.SystemDataService;
import dataservice.userdataservice.UserDataService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 单例模式
 *
 */
public class DataFactoryImpl extends UnicastRemoteObject implements DataFactory {

	private static final long serialVersionUID = 1L;

	private static DataFactoryImpl dataFactory;
	
	public DataFactoryImpl() throws RemoteException {
		super();
	}
	
	public static DataFactoryImpl getInstance() {
		if(dataFactory == null) {
			try {
				dataFactory = new DataFactoryImpl();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return dataFactory;
	}

	@Override
	public AccountDataService getAccountData() throws RemoteException {
		return new AccountDataServiceImpl();
	}

	@Override
	public CategoryDataService getCategoryData() throws RemoteException {
		return new CategoryDataServiceImpl();
	}

	@Override
	public CommodityDataService getCommodityData() throws RemoteException {
		return new CommodityDataServiceImpl();
	}

	@Override
	public CustomerDataService getCustomerData() throws RemoteException {
		return new CustomerDataServiceImpl();
	}

	@Override
	public ExceptionDataService getExceptionData() throws RemoteException {
		return new ExceptionDataServiceImpl();
	}

	@Override
	public WarningDataService getWarningData() throws RemoteException {
		return new WarningDataServiceImpl();
	}

	@Override
	public InitialDataService getInitialData() throws RemoteException {
		return new InitialDataServiceImpl();
	}

	@Override
	public LogDataService getLogData() throws RemoteException {
		return new LogDataServiceImpl();
	}

	@Override
	public MessageDataService getMessageData() throws RemoteException {
		return new MessageDataServiceImpl();
	}

	@Override
	public CashDataService getCashDataService() throws RemoteException {
		return new CashDataServiceImpl();
	}

	@Override
	public PaymentDataService getPaymentData() throws RemoteException {
		return new PaymentDataServiceImpl();
	}

	@Override
	public PresentDataService getPresentData() throws RemoteException {
		return new PresentDataServiceImpl();
	}
	
	@Override
	public CustomerGiftDataService getCustomerGiftData() throws RemoteException {
		return new CustomerGiftDataserviceImpl();
	}

	@Override
	public TotalGiftDataService getTotalGiftData() throws RemoteException {
		return new TotalGiftDataServiceImpl();
	}

	@Override
	public SpecialOfferDataService getSpecialOfferData() throws RemoteException {
		return new SpecialOfferDataServiceImpl();
	}

	@Override
	public PurchaseDataService getPurchaseData() throws RemoteException {
		return new PurchaseDataServiceImpl();
	}

	@Override
	public SaleDataService getSaleData() throws RemoteException {
		return new SaleDataServiceImpl();
	}

	@Override
	public StockDataService getStockData() throws RemoteException {
		return new StockDataServiceImpl();
	}

	@Override
	public UserDataService getUserData() throws RemoteException {
		return new UserDataServiceImpl();
	}

	@Override
	public SystemDataService getSystemData() throws RemoteException {
		return new SystemDataServiceImpl();
	}

}
