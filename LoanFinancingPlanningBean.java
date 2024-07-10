package com.brainyworks.marketbrain.cc.tb.administration.loanfinancingplanning;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.brainyworks.marketbrain.cc.base.constants.EmbDomainServiceNameConstants;
import com.brainyworks.marketbrain.cc.base.constants.EmbMessageIdConstants;
import com.brainyworks.marketbrain.cc.base.constants.EmbNavigationConstants;
import com.brainyworks.marketbrain.cc.core.EmbFacesBean;
import com.brainyworks.marketbrain.cc.core.constants.MessageIdConstants;
import com.brainyworks.marketbrain.cc.core.exception.SfaException;
import com.brainyworks.marketbrain.cc.core.proxy.ServiceProxyType;
import com.brainyworks.marketbrain.cc.core.state.EmbStateHandler;
import com.brainyworks.marketbrain.cc.core.state.EmbStateHandler.Scope;
import com.brainyworks.marketbrain.cc.core.util.FacesUtil;
import com.brainyworks.marketbrain.cc.core.util.ItemName;
import com.brainyworks.marketbrain.cc.core.util.ObjectUtil;
import com.brainyworks.marketbrain.cc.core.util.SfaArgumentUtil;
import com.brainyworks.marketbrain.cc.core.util.SfaValidationUtil;
import com.brainyworks.marketbrain.cc.core.util.excel.ExcelUtil;
import com.brainyworks.marketbrain.cc.tb.commonmodel.SheetNameCommonModel;
import com.brainyworks.marketbrain.cc.tb.trading.loan.loanfinancing.LoanFinancingUtils;
import com.brainyworks.marketbrain.tb.commonmodel.FileNameCommonModel;
import com.brainyworks.marketbrain.tb.commonmodel.LoanAttrCommonModel;
import com.brainyworks.marketbrain.tb.commonmodel.LoanDefaultCommonModel;
import com.brainyworks.marketbrain.tb.commonmodel.LoanFixCommonModel;
import com.brainyworks.marketbrain.tb.commonmodel.LoanFormatCommonModel;
import com.brainyworks.marketbrain.tb.commonmodel.LoanItemCommonModel;
import com.brainyworks.marketbrain.tb.commonmodel.LoanLinkItemCommonModel;
import com.brainyworks.marketbrain.tb.commonmodel.LoanSekkeiCommonModel;
import com.brainyworks.marketbrain.tb.commonmodel.LoanSetCommonModel;
import com.brainyworks.marketbrain.tb.commonmodel.LoanShowTextModel;
import com.brainyworks.marketbrain.tb.interfacemodel.tbadministration.loandesign.LoanExistSelectRequestModel;
import com.brainyworks.marketbrain.tb.interfacemodel.tbadministration.loandesign.LoanExistSelectResponseModel;
import com.brainyworks.marketbrain.tb.interfacemodel.tbadministration.loandesign.LoanRegistRequestModel;
import com.brainyworks.marketbrain.tb.interfacemodel.tbadministration.loandesign.LoanStartRequestModel;
import com.brainyworks.marketbrain.util.process.StringFormat;

/**
 * ローン各種申込設計(設計)
 * 作成日 : (2008/07/03)
 * @author: I.suzuki
 */
@SuppressWarnings("serial")
public class LoanFinancingPlanningBean extends EmbFacesBean {

    /** GitTest */
    private String gittest;

    /** GitHubからPull確認 */
    private String gitpull;

    /** Id(サービス種別) */
    private String id;

    /** 名称(ローンテーブル名) */
    private String name;

    /** 略称(ローンテーブル略称) */
    private String abbreviation;

    /** 設計サービス種類(ローンor他サービス) */
    private String designServiceKind;

    /** 設計状況 */
    private String designSituation;

    /** 設計可能項目コンボにて選択されたサービスのId(サービス種別) */
    private String selectedId;

    /** 設計可能項目コンボにて選択されたサービスの名称(ローンテーブル名) */
    private String selectedName;

    /** 設計可能項目コンボにて選択されたサービスの設計状況 */
    private String selectedDesignSituation;

    /** 金利選択ラジオの値 */
    private String interestRateSelectRadioValue;

    /** 金利(整数部) */
    private String interestRateInt;

    /** 金利(小数部) */
    private String interestRateDecimal;

    /** 年間返済額 */
    private String yearRepayment;

    /** 申込時の保証会社 */
    private String guarantyCompany;

    /** BooK */
    private String book;

    /** Sheet */
    private String sheet;

    /** 選択ボタン押下フラグ */
    private boolean isSelected;

    /** 選択可能項目コンボで選択された項目 */ 
    private String selectedService;

    /** 申込時の保証会社コンボ */
    private List<SelectItem> selectGuarantyCompanyList;

    /** Bookコンボ設定用リスト */
    private List<FileNameCommonModel> bookList = new ArrayList<FileNameCommonModel>();

    /** Sheetコンボ設定用リスト */
    private List<SheetNameCommonModel> sheetList = new ArrayList<SheetNameCommonModel>();

    /** BooKコンボ */
    private List<SelectItem> selectBooKList;

    /** Sheetコンボ */
    private List<SelectItem> selectSheetList;

    /** 選択可能項目コンボ */
    private List<SelectItem> selectableServiceList = new ArrayList<SelectItem>();

    /** 設計サービス種類ラジオでローンを選択しているか */
    private boolean isLoan;

    /** 金利選択種類ラジオで金利を選択しているか */
    private boolean isKinri;

    /** 属性変更ボタン押下時の項目名 */
    private String attributeChangeItemName;

    /** 属性変更ボタン押下フラグ */
    private boolean isAttributeChange;

    /** 登録済みフラグ */
    private boolean isRegisted;

    /** 選択可能項目リスト */
    private List<LoanItemListModel> selectableItemList;

    /** 作成項目リストセット用リスト */
    private List tempMakingItemList;

    /** 作成項目リスト */
    private List<LoanItemListModel> makingItemList;

    // 追加項目かを判断する為の初期情報退避用
    private List<LoanItemListModel> makingItemListBackUpInit;

    // 設計サービス種類ラジオボタン切替時の退避用
    /** 作成項目リストバックアップ(ローン) */
    private List<LoanItemListModel> makingItemListBackUpLoan;

    /** 作成項目リストバックアップ(その他サービス) */
    private List<LoanItemListModel> makingItemListBackUpOthers;

    /** ローン設計画面共通情報モデル */
    private LoanFinancingPlanningInfoModel loanInfo;

    /** 選択ローンの登録日時（排他制御用） */
    private String selectedLoanRegistDay;

    // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START
    /** 利用明細出力ラジオで有りを選択しているか */
    private boolean isRiyouMeisaiOut;

    /** 利用明細出力(有りor無し) */
    private String riyouMeisaiOut;

    /** 意思確認(有りor無し) */
    private String isiKakunin;

    /** 利用明細表示文言 */
    private String riyouMeisaiHyojiMongon;

    /** 改行（出力） */
    private static final String KAIGYO  = "\r\n";

    /** 利用明細表示文言の最大行数 */
    private static final int MAX_ROW_RIYOU_MEISAI_HYOJI_MONGON = 5;

    /** 利用明細表示文言の１行の最大文字数（全角） */
    private static final int MAX_MOJISU_ONE_ROW_MEISAI_HYOJI_MONGON = 40;

    /** 受付票フッター表示文言の最大行数 */
    private static final int MAX_ROW_UKETUKE_FOOTER_HYOJI_MONGON = 10;

    /** 受付票フッター表示文言の１行の最大文字数（全角） */
    private static final int MAX_MOJISU_ONE_ROW_UKETUKE_FOOTER_HYOJI_MONGON = 75;

    /** 利用明細出力(有りor無し) 初期表示時 */
    private String initRiyouMeisaiOut;
    // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END

    /** 金利小数部の最大バイト数 */
    private static final int MAX_BYTE_INTEREST_RATE_DECIMAL = 5;

    /**
     * 初期処理.<br/>
     * @param obj 初期化パラメータ
     */
    public void doInitialize(Object obj) {
        // パラメータの取得
        LoanFinancingPlanningBeanParams params = (LoanFinancingPlanningBeanParams) obj;

        this.loanInfo = (LoanFinancingPlanningInfoModel) EmbStateHandler.get("LOAN_INFO_KEY", Scope.SESSION);

        // 初期化
        this.initializeFieldsCommon();

        if (params.isSelected()) {
            this.initializeFieldsSelected(params);
        } else {
            this.initializeFieldsNew(params);
        }

        this.initializeFieldsCommonCombo();

        // 設計状況「設計中」か「サービス中」のとき、初期表示時のItemをバックアップ
        if (this.isDesigning() || this.isServing()) {
            this.makingItemListBackUpInit = (List<LoanItemListModel>) ObjectUtil.deepClone(this.makingItemList);
        }
    }

    /**
     * 登録処理.<br/>
     * @return ローン各種申込設計(サービス選択)論理名
     */
    public String doRegist() {

        // 入力チェック
        verifyRequiredOrDisplayConfirmation();

        // 登録処理
        executeRegistServise();

        super.chainBeanAction("loanFinancingPlanningSelectServiceBean.doInitialize");

        return EmbNavigationConstants.LOAN_FINANCING_PLANNING_SELECT_SERVICE;
    }

    /**
     * サービス開始処理.<br/>
     * @return ローン各種設計(設計)画面の論理名
     */
    public String doStartService() {

        // サービス開始処理
        executeStartService();

        this.designSituation = LoanFinancingPlanningConstants.DesignSituation.SERVING.code();

        addFacesMessage(EmbMessageIdConstants.INFO_START_SERVICE);

        return EmbNavigationConstants.LOAN_FINANCING_PLANNING;
    }

    /**
     * サービス終了処理.<br/>
     * @return ローン各種設計(設計)画面の論理名
     */
    public String doEndService() {

        // サービス開始処理
        executeStartService();

        this.designSituation = LoanFinancingPlanningConstants.DesignSituation.SERVICE_END.code();

        addFacesMessage(EmbMessageIdConstants.INFO_END_SERVICE);

        return EmbNavigationConstants.LOAN_FINANCING_PLANNING;
    }

    /**
     * 画面遷移(画面確認).<br/>
     * @return ローン各種申込設計(画面確認)論理名
     */
    public String doTransferDisplayConfirmation() {

        verifyRequiredOrDisplayConfirmation();

        LoanFinancingPlanningDisplayConfirmationBeanParams params =
            new LoanFinancingPlanningDisplayConfirmationBeanParams();
        params.setLoanTableName(this.name);
        params.setLoanItemList(this.makingItemList);
        params.setBook(this.book);
        params.setSheet(this.sheet);

        super.chainBeanAction("loanFinancingPlanningDisplayConfirmationBean.doInitialize", params); // 遷移先画面の初期処理

        super.setBackwardViewId(EmbNavigationConstants.LOAN_FINANCING_PLANNING);  // 遷移先画面の戻るボタン押下時に表示する画面

        return EmbNavigationConstants.LOAN_FINANCING_PLANNING_DISPLAY_CONFIRMATION;
    }

    /**
     * 画面遷移(項目設定(属性変更ボタン押下時)).<br/>
     * @return ローン各種申込設計(項目設定)論理名
     */
    public String doTransferItemSettingAttributeChange() {
        LoanItemListModel model = (LoanItemListModel) super.getRequestManagedBean("makingItemRow");

        LoanFinancingPlanningItemSettingBeanParams params = new LoanFinancingPlanningItemSettingBeanParams();
        params.setLoanItemListModel((LoanItemListModel) ObjectUtil.deepClone(model));

        super.chainBeanAction("loanFinancingPlanningItemSettingBean.doInitializeAttributeChange", params); // 遷移先画面の初期処理

        super.setBackwardViewId(EmbNavigationConstants.LOAN_FINANCING_PLANNING);  // 遷移先画面の戻るボタン押下時に表示する画面

        return EmbNavigationConstants.LOAN_FINANCING_PLANNING_ITEM_SETTING;
    }

    /**
     * 画面遷移(項目設定(項目追加ボタン押下時)).<br/>
     * @return ローン各種申込設計(項目設定)論理名
     */
    public String doTransferItemSettingNew() {

        super.chainBeanAction("loanFinancingPlanningItemSettingBean.doInitializeItemAdd"); // 遷移先画面の初期処理

        super.setBackwardViewId(EmbNavigationConstants.LOAN_FINANCING_PLANNING);  // 遷移先画面の戻るボタン押下時に表示する画面

        return EmbNavigationConstants.LOAN_FINANCING_PLANNING_ITEM_SETTING;
    }

    /**
     * 項目設定画面にて登録された項目をセット.<br/>
     * @param obj 項目設定画面から渡されたパラメータ
     */
    public void doSetItemSettingData(Object obj) {
        // パラメータの取得
        LoanFinancingPlanningBeanParams params = (LoanFinancingPlanningBeanParams) obj;

        LoanItemListModel loanItemModel = params.getLoanItemListModel();
        this.attributeChangeItemName = params.getAttributeChangeItemName();
        this.isAttributeChange = StringUtils.isNotEmpty(this.attributeChangeItemName);

        // パラメータチェック
        verifyParametersFromItemSetting(loanItemModel);

        if (this.isAttributeChange) {
            setItemSettigDataAttributeChange(loanItemModel);
        } else {
            this.makingItemList.add(loanItemModel);
            LoanFinancingUtils.getKoumokuSekkeiJyoukyou(loanItemModel, loanItemModel.getDefaultDivision()
                    , loanItemModel.getListSettingDivision(), loanItemModel.getLinkDivision());
        }

        this.attributeChangeItemName = "";
        this.isAttributeChange = false;
        addFacesMessage(EmbMessageIdConstants.INFO_LOAN_NOT_REGISTED_ITEM);
        // サービス開始ボタンを使用不可にする
        this.isRegisted = false;

        // 登録後、画面を初期常態で表示
        super.chainBeanAction("loanFinancingPlanningItemSettingBean.doInitializeItemAdd");
    }

    /**
     * 表示域切替処理(設計サービス種類ラジオボタンの非同期処理)
     * @param selectedCode 選択中の設計サービス種類ラジオボタンのコード値
     * @param selectedAcceptKbn 選択中の受付区分ラジオボタンのコード値
     * @param selectedIsiKakunin 選択中の意思確認ラジオボタンのコード値
     * @return 非同期で書き換えるエリアのHTML
     */
// 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START
//  public synchronized String[] doSelectServiceDivisionAsync(String selectedCode) {
    public synchronized String[] doSelectServiceDivisionAsync(
            String selectedCode, String selectedAcceptKbn, String selectedIsiKakunin) {
// 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END
        SfaArgumentUtil.assertNotEmpty(selectedCode);

        String[] html = null;

        this.designServiceKind = selectedCode;

        this.isLoan = this.designServiceKind.equals(LoanFinancingPlanningConstants.SERVICE_LOAN);
        if (this.isLoan) {
            this.makingItemListBackUpOthers = (List<LoanItemListModel>) ObjectUtil.deepClone(this.makingItemList);
            this.makingItemList = this.makingItemListBackUpLoan;
            // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START
            //意思確認を保持する
            this.isiKakunin = selectedIsiKakunin;
            // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END
        } else {
            this.makingItemListBackUpLoan = (List<LoanItemListModel>) ObjectUtil.deepClone(this.makingItemList);
            this.makingItemList = this.makingItemListBackUpOthers;
            // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START

            //意思確認を「有り」に設定する
            this.isiKakunin = LoanFinancingPlanningConstants.ISI_KAKUNIN_ARI;
            // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END
        }

        try {
            // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START
            //html = new String[2];
            html = new String[3];
           // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END

            FacesUtil.restoreView(super.getFacesContext(), EmbNavigationConstants.LOAN_FINANCING_PLANNING);

            html[0] = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:hosyouGaisyaGroup");
            html[1] = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:sakuseiItemListGroup");
            // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START
            html[2] = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:isiKakuninGrid");
            // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END

        } catch (Throwable t){
            super.handleAsynchronousException(t);
        }
        return html;
    }

    /**
     * 表示域切替処理(Bookコンボの非同期処理)
     * @param selectedCode 選択中のBookコンボの値
     * @return 非同期で書き換えるエリアのHTML
     */
    public synchronized String doSelectSheetComboAsync(String selectedCode) {

        String html = "";

        this.book = selectedCode;

        try {

            this.sheet = "";
            setSheetList();

            FacesUtil.restoreView(super.getFacesContext(), EmbNavigationConstants.LOAN_FINANCING_PLANNING);

            html = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:sheetCombo");

        } catch (Throwable t) {
            super.handleAsynchronousException(t);
        }
        return html;
    }

    /**
     * 表示域切替処理(金利選択ラジオボタンの非同期処理)
     * @param selectedCode 選択中の金利選択ラジオボタンのコード値
     * @return 非同期で書き換えるエリアのHTML
     */
    public synchronized String doSelectKinriDivisionAsync(String selectedCode) {
        SfaArgumentUtil.assertNotEmpty(selectedCode);

        String html = "";
        this.interestRateSelectRadioValue = selectedCode;
        this.isKinri = this.interestRateSelectRadioValue.equals(LoanFinancingPlanningConstants.INTEREST_RATE);

        try {
            FacesUtil.restoreView(super.getFacesContext(), EmbNavigationConstants.LOAN_FINANCING_PLANNING);

            html = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:kinriSentakuInputGroup");

        } catch (Throwable t){
            super.handleAsynchronousException(t);
        }
        return html;
    }

    /**
     * 表示域切替処理(選択可能項目コンボ非同期処理)
     * @param selectedServiceId 選択中の選択可能項目コンボのコード値
     * @return 非同期で書き換えるエリアのHTML
     */
    public synchronized String doSelectListDivisionAsync(String selectedServiceId) {
        String html = "";
        this.selectedService = selectedServiceId;

        try {
            // 入力チェック
            verifyRequiredSeletableService(this.selectedService);

            List <LoanSekkeiCommonModel> sekkeiList = this.loanInfo.getLoanSekkeiV();

            for (LoanSekkeiCommonModel sekkeiModel : sekkeiList) {
                if (this.selectedService.equals(sekkeiModel.getLoanTableName())) {
                    this.selectedId = sekkeiModel.getServiceSyubetsu();             // サービス種別
                    this.selectedName = this.selectedService;                      // 名称
                    this.selectedDesignSituation = sekkeiModel.getSekkeiJyokyo();   // 設計状況
                    break;
                }
            }
            executeSelectableServiceListDetail();
            FacesUtil.restoreView(super.getFacesContext(), EmbNavigationConstants.LOAN_FINANCING_PLANNING);
            html = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:selectableItemListGroup");

        } catch (Throwable t){
            super.handleAsynchronousException(t);
        }
        return html;
    }

    /**
     * 選択行を別テーブルへ追加.(非同期処理)
     * @param asyncSelectedId 選択行のID
     * @return 書き換えるHTML
     */
    public synchronized String[] doAddSelectionTableAsync(String asyncSelectedId) {
        // 引数の空チェック
        SfaArgumentUtil.assertNotEmpty(asyncSelectedId);
        String[] html = null;

        try {
            html = new String[2];

            // 選択行のIDより自一覧から対象の一覧へデータを移動
            for(LoanItemListModel selectableRow : selectableItemList) {
                if(selectableRow.getItemName().equals(asyncSelectedId)) {
                    if (isPossibleAdd(selectableRow)) {
                        setSelectShopList(selectableRow);
                        makingItemList.add(selectableRow);
                        break;
                    }
                }
            }
            FacesUtil.restoreView(super.getFacesContext(), EmbNavigationConstants.LOAN_FINANCING_PLANNING);

            html[0] = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:selectableItemListGroup");
            html[1] = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:sakuseiItemListGroup");

        } catch (Throwable t){
            super.handleAsynchronousException(t);
        }
        return html;
    }

    /**
     * 選択行を削除.(非同期処理)
     * @param asyncSelectedId 選択行のID
     * @return 書き換えるHTML
     */
    public synchronized String[] doDeleteSelectionTableAsync(String asyncSelectedId) {
        // 引数の空チェック
        SfaArgumentUtil.assertNotEmpty(asyncSelectedId);

        String[] html = null;

        try {
            html = new String[2];

            // 選択行のIDより自一覧から対象の一覧へデータを移動
            for(LoanItemListModel makingRow : makingItemList) {
                if(makingRow.getItemName().equals(asyncSelectedId)) {
                    if (isPossibleDelete(makingRow)) {
                        makingItemList.remove(makingRow);
                        break;
                    }
                }
            }

            FacesUtil.restoreView(super.getFacesContext(), EmbNavigationConstants.LOAN_FINANCING_PLANNING);

            html[0] = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:selectableItemListGroup");
            html[1] = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:sakuseiItemListGroup");

        } catch (Throwable t){
            super.handleAsynchronousException(t);
        }
        return html;
    }

    /**
     * 一覧の選択行を上へ移動(非同期処理).<br/>
     * @param asyncSelectedId 選択行のID
     * @return 非同期で書き換えるエリアのHTML
     */
    public synchronized String doUpAsync(String asyncSelectedId) {
        // 引数の空チェック
        SfaArgumentUtil.assertNotEmpty(asyncSelectedId);

        String html = "";

        try {
            // 一覧の選択行の上下を入れ替える
            for(LoanItemListModel makingListModel : this.makingItemList) {
                if(makingListModel.getItemName().equals(asyncSelectedId)) {
                    int rowNumber = this.makingItemList.indexOf(makingListModel);
                    if (rowNumber == 0) { break; }
                    this.replacePosition(rowNumber - 1, rowNumber);
                    break;
                }
            }

            FacesUtil.restoreView(super.getFacesContext(), EmbNavigationConstants.LOAN_FINANCING_PLANNING);

            html = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:sakuseiItemListGroup");

        } catch (Throwable t){
            super.handleAsynchronousException(t);
        }
        return html;
    }

    /**
     * 一覧の選択行の下移動(非同期処理).<br/>
     * @param asyncSelectedId 選択行のID
     * @return 非同期で書き換えるエリアのHTML
     */
    public synchronized String doDownAsync(String asyncSelectedId) {
        SfaArgumentUtil.assertNotEmpty(asyncSelectedId);

        String html = "";
        try {
            // 一覧の選択行の上下を入れ替える
            for(LoanItemListModel makingListModel : this.makingItemList) {
                if(makingListModel.getItemName().equals(asyncSelectedId)) {
                    int rowNumber = this.makingItemList.indexOf(makingListModel);
                    if (rowNumber == this.makingItemList.size() - 1) { break; }
                    this.replacePosition(rowNumber, rowNumber + 1);
                    break;
                }
            }

            FacesUtil.restoreView(super.getFacesContext(), EmbNavigationConstants.LOAN_FINANCING_PLANNING);

            html = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:sakuseiItemListGroup");
        } catch (Throwable t){
            super.handleAsynchronousException(t);
        }
        return html;
    }

    /**
     * 入力に変更があったとき、サービス開始ボタンを使用不可とする.<br/>
     * @return 非同期で書き換えるエリアのHTML
     */
    public synchronized String doSelectServiceStartButtonDivisionAsync() {
        String html = "";
        try {
            this.isRegisted = false;
            FacesUtil.restoreView(super.getFacesContext(), EmbNavigationConstants.LOAN_FINANCING_PLANNING);

            html = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:serviseStartButtonGroup");
        } catch (Throwable t){
            super.handleAsynchronousException(t);
        }
        return html;
    }


    /**
     * 共通各フィールドの初期化.<br/>
     */
    private void initializeFieldsCommon() {
        this.selectGuarantyCompanyList  = null;
        this.selectBooKList = null;
        this.selectSheetList = null;
        this.tempMakingItemList = new ArrayList<LoanItemListModel>();
        this.selectableItemList = null;
        this.makingItemList = null;
        this.guarantyCompany = "";
        this.book = "";
        this.sheet = "";
        this.interestRateInt = "";
        this.interestRateDecimal = "";
        this.yearRepayment = "";
        this.isRegisted = true;
        this.interestRateSelectRadioValue = LoanFinancingPlanningConstants.INTEREST_RATE;
        this.selectedService = "";
        this.attributeChangeItemName = "";
        this.isAttributeChange = false;
        this.selectedLoanRegistDay = "";
        // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START
        this.riyouMeisaiOut = LoanFinancingPlanningConstants.RIYOUMEISAI_OUT_ARI;
        this.isiKakunin = LoanFinancingPlanningConstants.ISI_KAKUNIN_ARI;
        this.riyouMeisaiHyojiMongon = "";
        this.initRiyouMeisaiOut = "";
        // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END
    }

    /**
     * 共通各フィールドの初期化(コンボ).<br/>
     */
    private void initializeFieldsCommonCombo() {
        this.selectGuarantyCompanyList = FacesUtil.toSelectItems(this.loanInfo.getLoanHosyoV(), "code", "name", true);

        this.bookList = this.loanInfo.getExcelFileNameV();

        setSheetList();

        this.selectBooKList = FacesUtil.toSelectItems(this.bookList, "fileName", "fileName", true);
        this.selectSheetList = FacesUtil.toSelectItems(this.sheetList, "sheetName", "sheetName", true);
        this.selectableServiceList = FacesUtil.toSelectItems(
                this.loanInfo.getLoanSekkeiV(), "loanTableName", "loanTableName", true);

    }

    /**
     * シートリストを設定.<br/>
     */
    private void setSheetList() {
        if (StringUtils.isEmpty(this.book)) {
            this.sheetList = new ArrayList<SheetNameCommonModel>();
        } else {
            this.sheetList = ExcelUtil.getSheetName(this.book);
        }
        this.selectSheetList = FacesUtil.toSelectItems(this.sheetList, "sheetName", "sheetName", true);
        if (!StringUtils.isEmpty(this.book)
                && this.sheetList.size() < 1) {
            throw new SfaException(MessageIdConstants.EXCEL_OPEN_ERROR);
         }
    }

    /**
     * 各フィールドの初期化(新規以外).<br/>
     */
    private void initializeFieldsSelected(LoanFinancingPlanningBeanParams params) {

        this.id = params.getLoanSekkeiModel().getServiceSyubetsu();
        this.name = params.getLoanSekkeiModel().getLoanTableName();
        this.abbreviation = params.getLoanSekkeiModel().getLoanTableRyakusyo();
        this.designServiceKind = params.getLoanSekkeiModel().getServiceKbn();
        this.designSituation = params.getLoanSekkeiModel().getSekkeiJyokyo();
        this.isSelected = true;
        this.isLoan = this.designServiceKind.equals(LoanFinancingPlanningConstants.SERVICE_LOAN);
        this.guarantyCompany = params.getLoanSekkeiModel().getHosyoKaisyaId();
        this.book = params.getLoanSekkeiModel().getBook();
        this.sheet = params.getLoanSekkeiModel().getSheet();

        String[] interestRateList = params.getLoanSekkeiModel().getKinri().split("\\.");
        this.interestRateInt = interestRateList[0];
        // 整数部小数部共に値が有る場合
        if (interestRateList.length == 2) {
            // 右ゼロ埋めを行った値をセット
            this.interestRateDecimal = StringFormat.fillZeroToRight(
                    interestRateList[1], MAX_BYTE_INTEREST_RATE_DECIMAL);
        // 整数部に値が無い場合(年間返済額選択)
        } else if (this.interestRateInt.equals("")){
            this.interestRateDecimal = "";
        // 整数部に値が有り、小数部に値が無い場合(登録値が整数のデータ)
        } else {
            this.interestRateDecimal = "00000";
        }
        this.yearRepayment = params.getLoanSekkeiModel().getNenkanHensaiGaku();
        this.isKinri = StringUtils.isEmpty(this.yearRepayment);
        if (this.isKinri) {
            this.interestRateSelectRadioValue = LoanFinancingPlanningConstants.INTEREST_RATE;
        } else {
            this.interestRateSelectRadioValue = LoanFinancingPlanningConstants.YEAR_REPAMENT;
        }
        this.selectedLoanRegistDay = params.getLoanSekkeiModel().getRegistDay();
        this.makingItemList = params.getMakingItemList();
        // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START
        //利用明細出力
        this.riyouMeisaiOut = params.getLoanSekkeiModel().getFieldDetailOutput();

        // 画面初期表示時の利用明細出力の選択値を保持
        this.initRiyouMeisaiOut = params.getLoanSekkeiModel().getFieldDetailOutput();

        this.isRiyouMeisaiOut = this.riyouMeisaiOut.equals(LoanFinancingPlanningConstants.RIYOUMEISAI_OUT_NASI);

        //意思確認
        this.isiKakunin = params.getLoanSekkeiModel().getFieldRepetitionUse();

        //利用明細表示文言
        Vector<LoanShowTextModel> tmpFieldDetailTextV = params.getLoanSekkeiModel().getFieldDetailTextV();
        StringBuffer tmpMeisaiMongon = new StringBuffer();
        LoanShowTextModel tmpModel = null;

        for (int i = 0; i < tmpFieldDetailTextV.size(); i++) {
            tmpModel = tmpFieldDetailTextV.get(i);

            if (i != 0) {
                tmpMeisaiMongon.append(KAIGYO);
            }

            tmpMeisaiMongon.append(tmpModel.getFieldText());
        }

        this.riyouMeisaiHyojiMongon = tmpMeisaiMongon.toString();
        tmpModel = null;

        // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END
    }

    /**
     * 各フィールドの初期化(新規).<br/>
     */
    private void initializeFieldsNew(LoanFinancingPlanningBeanParams params) {
        this.id = params.getId();
        this.name = params.getName();
        this.abbreviation = params.getAbbreviation();
        this.designSituation = LoanFinancingPlanningConstants.DesignSituation.NEW.code();
        this.designServiceKind = LoanFinancingPlanningConstants.SERVICE_LOAN;
        this.isSelected = false;
        this.isLoan = true;
        this.isKinri = true;
        // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START
        this.isRiyouMeisaiOut = false;
        // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END
        this.makingItemList = setItemListFromFixModel(this.tempMakingItemList);
        this.makingItemListBackUpInit = new ArrayList<LoanItemListModel>();
        this.makingItemListBackUpLoan = new ArrayList<LoanItemListModel>();
        this.makingItemListBackUpOthers = new ArrayList<LoanItemListModel>();
        // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START
        //利用明細出力
        this.riyouMeisaiOut = LoanFinancingPlanningConstants.RIYOUMEISAI_OUT_ARI;

        //意思確認
        this.isiKakunin = LoanFinancingPlanningConstants.ISI_KAKUNIN_ARI;

        //利用明細表示文言
        this.riyouMeisaiHyojiMongon = "";

        // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END
    }


    /**
     * 既存サービス選択・設定処理.<br/>
     */
    private void executeSelectableServiceListDetail() {

        LoanExistSelectRequestModel request = new LoanExistSelectRequestModel();

        LoanSekkeiCommonModel model = new LoanSekkeiCommonModel();
        model.setLoanTableName(this.selectedName);
        model.setServiceSyubetsu(this.selectedId);
        model.setServiceKbn(this.designServiceKind);
        model.setSekkeiJyokyo(this.selectedDesignSituation);

        request.setLoanLinkItemV(this.loanInfo.getLoanLinkItemV());
        request.setLoanSekkeiM(model);


        // TB業務共通要求情報をセット
        request.setTbGyomuCommonReqM(LoanFinancingUtils.getTbGyoumuCommonRequest());

        LoanExistSelectResponseModel response =
            (LoanExistSelectResponseModel) super.invoke(ServiceProxyType.DOMAIN_SERVICE,
                    EmbDomainServiceNameConstants.LOAN_EXIST_SELECT_DOMAIN_INTERFACE , request);

        List<LoanItemListModel> tempList = new ArrayList<LoanItemListModel>();
        this.selectableItemList = LoanFinancingUtils.setItemListFromItemModel(
                tempList, response.getLoanSetM().getLoanItemV(), this.loanInfo.getLinkItemV());
    }

    /**
     * 登録処理.<br/>
     */
    private void executeRegistServise() {
        LoanRegistRequestModel  request = new LoanRegistRequestModel();

        LoanSetCommonModel setModel = new LoanSetCommonModel();

        setModel.setLoanSekkeiM(getParamLoanSekkeiModel());

        Vector itemList = new Vector();
        setItemModelFromItemList(itemList);
        setModel.setLoanItemV(itemList);

        request.setLoanSetM(setModel);

        // TB業務共通要求情報をセット
        request.setTbGyomuCommonReqM(LoanFinancingUtils.getTbGyoumuCommonRequest());

        // 2010.06.25 青森対応　新規登録フラグを追加
        if (!this.isSelected) {
            request.setNewRegist("1");
        }
//        TelbanCommonResponseModel resM = (TelbanCommonResponseModel) super.invoke(
//                ServiceProxyType.DOMAIN_SERVICE,
//                EmbDomainServiceNameConstants.LOAN_REGIST_DOMAIN_INTERFACE, request);
//        if (!StringUtility.isEmpty(resM.getGyomuCommonResM().getReturnCode())) {
//            if (resM.getGyomuCommonResM().getReturnCode().equals("MB92012W")) {
//                throw new SfaException(
//                    EmbMessageIdConstants.ERROR_FUND_SWITCHING_GROUP_ALREADY_EXIST);
//            }
//        }
        super.invoke(ServiceProxyType.DOMAIN_SERVICE,
                EmbDomainServiceNameConstants.LOAN_REGIST_DOMAIN_INTERFACE, request);
    }

    /**
     * ローン設計パラメータを取得.<br/>
     * @return ローン設計パラメータ
     */
    private LoanSekkeiCommonModel getParamLoanSekkeiModel() {
        LoanSekkeiCommonModel sekkeiModel = new LoanSekkeiCommonModel();
        sekkeiModel.setLoanTableName(this.name);
        sekkeiModel.setLoanTableRyakusyo(this.abbreviation);
        sekkeiModel.setServiceSyubetsu(this.id);
        sekkeiModel.setServiceKbn(this.designServiceKind);
        if (this.isLoan) {
            sekkeiModel.setHosyoKaisyaId(this.guarantyCompany);
            if (this.isKinri) {
                sekkeiModel.setKinri(addDecimalPoint(this.interestRateInt, this.interestRateDecimal));
            } else {
                sekkeiModel.setNenkanHensaiGaku(this.yearRepayment);
            }
        }
        sekkeiModel.setBook(this.book);
        sekkeiModel.setSheet(this.sheet);
        if (this.designSituation.equals(LoanFinancingPlanningConstants.DesignSituation.NEW.code())) {
            sekkeiModel.setSekkeiJyokyo(LoanFinancingPlanningConstants.DesignSituation.DESIGNING.code());
        } else {
            sekkeiModel.setSekkeiJyokyo(this.designSituation);
        }

        sekkeiModel.setRegistDay(this.selectedLoanRegistDay);
        // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START
        //受付区分
        sekkeiModel.setFieldReceiptDivision("0");
        //利用明細出力
        sekkeiModel.setFieldDetailOutput(this.riyouMeisaiOut);
        //意思確認
        sekkeiModel.setFieldRepetitionUse(this.isiKakunin);
        //利用明細表示文言（改行コードでsplitしてModelに格納する）
        if (!"".equals(this.riyouMeisaiHyojiMongon)) {
            Vector<LoanShowTextModel> fieldDetailTextV = sekkeiModel.getFieldDetailTextV();

            createLoanShowTextModel(
                    fieldDetailTextV
                    , this.name
                    , this.riyouMeisaiHyojiMongon
                    , MAX_MOJISU_ONE_ROW_MEISAI_HYOJI_MONGON);

        }
        // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END

        return sekkeiModel;
    }

    /**
     * サービス開始/終了処理.<br/>
     */
    private void executeStartService() {
        LoanStartRequestModel request = new LoanStartRequestModel();

        LoanSekkeiCommonModel model = new LoanSekkeiCommonModel();
        model.setLoanTableName(this.name);
        model.setLoanTableRyakusyo(this.abbreviation);
        model.setServiceSyubetsu(this.id);
        model.setServiceKbn(this.designServiceKind);
        if (this.isLoan) {
            model.setHosyoKaisyaId(this.guarantyCompany);
            if (this.isKinri) {
                model.setKinri(addDecimalPoint(this.interestRateInt, this.interestRateDecimal));
            } else {
                model.setNenkanHensaiGaku(this.yearRepayment);
            }
        }
        model.setBook(this.book);
        model.setSheet(this.sheet);
        // NSD ADD START 2013/04/05 サービス開始・終了時に時刻による排他制御追加
        model.setRegistDay(this.selectedLoanRegistDay);
        // NSD ADD END   2013/04/05
        if (this.isDesigning()) {
            model.setSekkeiJyokyo(LoanFinancingPlanningConstants.DesignSituation.SERVING.code());
        } else {
            model.setSekkeiJyokyo(LoanFinancingPlanningConstants.DesignSituation.SERVICE_END.code());
        }
        // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START
        model.setFieldDetailOutput(this.initRiyouMeisaiOut);
        // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END
        request.setLoanSekkeiM(model);

        // TB業務共通要求情報をセット
        request.setTbGyomuCommonReqM(LoanFinancingUtils.getTbGyoumuCommonRequest());

        super.invoke(ServiceProxyType.DOMAIN_SERVICE,
                EmbDomainServiceNameConstants.LOAN_START_DOMAIN_INTERFACE, request);
    }

    /**
     * 入力チェック(登録・出力ボタン押下時の必須チェック).<br/>
     */
    private void verifyRequiredOrDisplayConfirmation() {
        if (this.isLoan) {
            // 申込時の保証会社の必須チェック
            SfaValidationUtil.validateRequiredSelect(this.guarantyCompany, ItemName.get("moushikomijinohosyougaisya"));
        }
        // Bookの必須チェック
        SfaValidationUtil.validateRequiredSelect(this.book, ItemName.get("book"));
        // Sheetの必須チェック
        SfaValidationUtil.validateRequiredSelect(this.sheet, ItemName.get("sheet"));
        // 金利選択の必須チェック
        SfaValidationUtil.validateRequiredSelect(this.interestRateSelectRadioValue, ItemName.get("kinrisentaku"));
        if (this.isLoan) {
            if (this.isKinri) {
                // 金利（整数部）の必須チェック
                SfaValidationUtil.validateRequiredInput(
                        this.interestRateInt, ItemName.get("kinri") + ItemName.get("seisuubu"));
                // 金利（小数部）の必須チェック
                SfaValidationUtil.validateRequiredInput(
                        this.interestRateDecimal, ItemName.get("kinri") + ItemName.get("syousuubu"));
                // エラーがある場合、メッセージを出力
                super.handleValidatorException();
                // 金利が0よりも大きい値であるかのチェック
                SfaValidationUtil.validateNumberExcludeMinimum(
                        this.interestRateInt + "." + this.interestRateDecimal, "0", ItemName.get("kinri"));
            } else {
                // 年間返済額の必須チェック
                SfaValidationUtil.validateRequiredInput(this.yearRepayment, ItemName.get("nenkanhensaigaku"));
                // 年間返済額が0よりも大きい値であるかのチェック
                SfaValidationUtil.validateNumberExcludeMinimum(
                        this.yearRepayment, "0", ItemName.get("nenkanhensaigaku"));
            }
        }

        // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START
        // 改行コードを含めない利用明細表示文言
        String tmpAllMeisaiMongon = this.riyouMeisaiHyojiMongon.replace(KAIGYO, "");

        // 利用明細表示文言をＤＢに格納する際の合計行数
        int riyouMeisaiTotalRowCnt;

        // 受付票フッター表示文言をＤＢに格納する際の合計行数
        int footerTotalRowCnt;

        // 利用明細表示文言の全角チェック
        SfaValidationUtil.validateDoubleByte(tmpAllMeisaiMongon, ItemName.get("riyoumeisaihyoujimongon"));

        // 利用明細表示文言のバイト数上限チェック（400バイトを超えるとエラー）
        if(StringUtils.isNotBlank(this.riyouMeisaiHyojiMongon)){
            SfaValidationUtil.validateByteMaximum(
                    tmpAllMeisaiMongon, 400, ItemName.get("riyoumeisaihyoujimongon"));
        }

        // 利用明細表示文言の総行数をチェック
        riyouMeisaiTotalRowCnt = calcTotalRowCount(
                this.riyouMeisaiHyojiMongon, MAX_MOJISU_ONE_ROW_MEISAI_HYOJI_MONGON);

        if (MAX_ROW_RIYOU_MEISAI_HYOJI_MONGON < riyouMeisaiTotalRowCnt) {
            addFacesMessage(EmbMessageIdConstants.USE_DETAIL_OVER_TEXT);
        }

        // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END

        // エラーがある場合、メッセージを出力
        super.handleValidatorException();

        // 作成項目一覧の必須チェック
        if (this.makingItemList.size() <= 0) {
            throw new SfaException(MessageIdConstants.VALIDATION_REQUIRED_INPUT, ItemName.get("sakuseikoumoku"));
        }
    }

    /**
     * 項目設定画面から遷移時のパラメータチェック.<br/>
     * @param paramItem 項目設定画面から渡されたパラメータ
     */
    private void verifyParametersFromItemSetting(LoanItemListModel paramItem) {
        // エクセルのセル指定・項目名・カラム名名重複、最大項目数オーバーチェック
        isPossibleAdd(paramItem);

        if (this.isAttributeChange) {

            // サービス中ローンの長さチェック(属性変更ボタン押下時のみ)
            if (isServing()
                    && !isItemAdded(this.attributeChangeItemName)) {
                for (LoanItemListModel makingItem : this.makingItemList) {
                    if (makingItem.getItemName().equals(this.attributeChangeItemName)
                            && Integer.valueOf(makingItem.getLength()) > Integer.valueOf(paramItem.getLength())) {
                        throw new SfaException(EmbMessageIdConstants.WARNING_LOAN_CANNOT_SHORT);
                    }
                }
            }
        }
    }

    /**
     * 作成項目一覧に追加可能かを判断.<br/>
     * @param selectableRow 選択行
     * @throws エクセルのセル指定重複エラー
     * @throws 項目名重複エラー
     * @throws カラム名重複エラー
     * @throws 最大項目数オーバーエラー
     * @return 選択行を別テーブルへ追加可能か
     */
    private boolean isPossibleAdd(LoanItemListModel selectableRow) {
        for(LoanItemListModel makingRow : this.makingItemList) {
            // 属性変更ボタン押下後の場合、ボタン押下前と同じ内容でもOK
            if (!StringUtils.equals(this.attributeChangeItemName, makingRow.getItemName())) {
                // エクセルのセル指定重複チェック
                if (selectableRow.getCellPosition().equals(makingRow.getCellPosition())) {
                    throw new SfaException(
                            EmbMessageIdConstants.WARNING_LOAN_OVERLAPS_LIST_ITEM, ItemName.get("excelnocellshitei"));
                }
                // 項目名の重複チェック
                if (selectableRow.getItemName().equals(makingRow.getItemName())) {
                    throw new SfaException(
                            EmbMessageIdConstants.WARNING_LOAN_OVERLAPS_LIST_ITEM, ItemName.get("koumokumei"));
                }
                // カラム名の重複チェック
                if (selectableRow.getColumnName().equals(makingRow.getColumnName())) {
                    throw new SfaException(
                            EmbMessageIdConstants.WARNING_LOAN_OVERLAPS_LIST_ITEM, ItemName.get("columnmei"));
                }
            }
        }

        // 最大項目数オーバーチェック(属性変更ボタン押下後以外のとき)
        if (!isAttributeChange
                && makingItemList.size() == LoanFinancingPlanningConstants.MAX_LOAN_ITEM) {
            throw new SfaException(EmbMessageIdConstants.WARNING_LOAN_OVER_MAX);
        }
        return true;
    }

    /**
     * 作成項目一覧に追加するとき営業店リストを設定.<br/>
     * @param selectableRow 作成可能項目一覧の一行
     */
    private void setSelectShopList(LoanItemListModel listItem) {
        // 属性が営業店選択型のとき
        if (listItem.getAttributeId().equals(LoanFinancingPlanningConstants.ATTRIBUTE_SELECT_SHOP)) {
            listItem.setLoanSelectionV(this.loanInfo.getLinkItemV());
        }
    }

    /**
     * 作成項目一覧から削除可能かを判断.<br/>
     * @param makingRow 選択行のID
     * @throws 削除不可能エラー(固定項目)
     * @throws 削除不可能エラー(サービス中)
     * @return 作成項目一覧から削除可能か
     */
    private boolean isPossibleDelete(LoanItemListModel makingRow) {
        // 固定項目チェック
        if (this.isLoan
                && LoanFinancingPlanningConstants.FIX_ITEM.equals(makingRow.getFixFlag())) {
            throw new SfaException(EmbMessageIdConstants.WARNING_LOAN_DELETE_IMPOSSIBLE);
        }
        // 設計状況がサービス中で、かつ登録済みの項目は削除不可)
        if (isServing() && !isItemAdded(makingRow.getItemName())) {
            throw new SfaException(EmbMessageIdConstants.WARNING_LOAN_DELETE_SERVING);
        }
        return true;
    }

    /**
     * 入力チェック(選択可能項目コンボ).<br/>
     */
    private void verifyRequiredSeletableService(String selectedServiceTableName) {
        // 選択可能項目コンボの入力チェック
        SfaValidationUtil.validateRequiredSelect(selectedServiceTableName, ItemName.get("sentakukanoukoumoku"));

        // エラーがある場合、メッセージを出力
        super.handleValidatorException();
    }

    /**
     * 小数点を付加した値を返却.<br/>
     * @param intValue 整数部の値
     * @param decimalValue 小数部の値
     * @return 小数点を付加した値
     */
    private String addDecimalPoint(String intValue, String decimalValue) {
        if (StringUtils.equals(intValue, "") && StringUtils.equals(decimalValue, "")) {
            return "";
        }
        if (StringUtils.equals(decimalValue, "")) {
            return intValue;
        }
        return intValue + "." + decimalValue;
    }

    /**
     * リスト内の行の位置を入れ替え.<br/>
     * @param forwardIndex 前方インデックス
     * @param followingIndex 後方インデックス
     */
    private void replacePosition(int forwardIndex, int followingIndex) {
        LoanItemListModel tempSelectItemList1 = this.makingItemList.get(forwardIndex);
        LoanItemListModel tempSelectItemList2 = this.makingItemList.get(followingIndex);

        this.makingItemList.set(forwardIndex, tempSelectItemList2);
        this.makingItemList.set(followingIndex, tempSelectItemList1);
    }

    /**
     * ローン固定項目モデルから作成項目リストを設定.<br/>
     */
    private List <LoanItemListModel> setItemListFromFixModel(List<LoanItemListModel> tempList) {

        List<LoanFixCommonModel> fixList = (List<LoanFixCommonModel>) this.loanInfo.getLoanFixV();

        for (LoanFixCommonModel fixModel : fixList) {
            LoanItemListModel loanItem = new LoanItemListModel();
            loanItem.setItemName(fixModel.getKomokuName());
            loanItem.setColumnName(fixModel.getColumnName());
            loanItem.setAttributeId(fixModel.getAttr());
            loanItem.setInputFormatId(fixModel.getFormat());
            loanItem.setLength(fixModel.getLength());
            loanItem.setIdLength(fixModel.getIdLength());
            loanItem.setCellPosition(fixModel.getCellPos());
            loanItem.setListSettingDivision(fixModel.getListSetteiKbn());
            loanItem.setDefaultDivision(fixModel.getDefaultKbn());
            loanItem.setLinkDivision(fixModel.getLinkKbn());
            loanItem.setInitialId(fixModel.getInitId());
            loanItem.setRequiredDivision(fixModel.getHissu());
            loanItem.setFixFlag(LoanFinancingPlanningConstants.FIX_ITEM);
            LoanFinancingUtils.getKoumokuSekkeiJyoukyou(loanItem, loanItem.getDefaultDivision()
                    , loanItem.getListSettingDivision(), loanItem.getLinkDivision());
            setSelectShopList(loanItem);

            tempList.add(loanItem);
        }
        return tempList;
    }

    /**
     * 登録する値を設定.<br/>
     * 作成項目リストからローン項目設定.<br/>
     * @param itemList ローン項目
     */
    private void setItemModelFromItemList(Vector itemList) {

        for (LoanItemListModel makingItem : this.makingItemList) {
            LoanItemCommonModel itemModel = new LoanItemCommonModel();
            itemModel.setKomokuName(makingItem.getItemName());
            itemModel.setColumnName(makingItem.getColumnName());

            if (this.isDesigning() || this.isServing()) {
                if (isItemAdded(makingItem.getItemName())) {
                    itemModel.setItemAddFlag(LoanFinancingPlanningConstants.LIST_ITEM_ADDED);
                } else {
                    itemModel.setItemAddFlag(LoanFinancingPlanningConstants.LIST_ITEM_EXISTING);
                }
            }

            LoanAttrCommonModel attrModel = new LoanAttrCommonModel();
            attrModel.setAttrId(makingItem.getAttributeId());
            itemModel.setLoanAttrM(attrModel);

            LoanFormatCommonModel formatModel = new LoanFormatCommonModel();
            formatModel.setFormatId(makingItem.getInputFormatId());
            itemModel.setLoanFormatM(formatModel);

            itemModel.setLength(makingItem.getLength());
            itemModel.setCellPos(makingItem.getCellPosition());

            itemModel.setLoanSelectionV(makingItem.getLoanSelectionV());


            LoanDefaultCommonModel defaultModel = new LoanDefaultCommonModel();
            defaultModel.setDefaultId(makingItem.getInitialId());
            itemModel.setLoanDefaultM(defaultModel);

            LoanLinkItemCommonModel linkModel = new LoanLinkItemCommonModel();
            linkModel.setIdSize(makingItem.getIdLength());
            itemModel.setLoanLinkItemM(linkModel);

            itemModel.setFixFlag(makingItem.getFixFlag());
            itemModel.setSelectionFlag(makingItem.getListSettingDivision());
            itemModel.setDefaultFlag(makingItem.getDefaultDivision());
            itemModel.setLinkFlag(makingItem.getLinkDivision());
            itemModel.setHissu(makingItem.getRequiredDivision());

            itemList.add(itemModel);
        }
    }

    /**
     * 対象項目が、追加された項目か登録済みの項目かを返却.<br/>
     * @param targetItemName チェック対象項目の項目名
     * @return 追加された項目か登録済みの項目か
     */
    private boolean isItemAdded(String targetItemName) {
        for (LoanItemListModel backUpItem : this.makingItemListBackUpInit) {
            if (targetItemName.equals(backUpItem.getItemName())) {
                return false;
            }
        }
        return true;
    }


    /**
     * 属性変更ボタン押下時、項目設定画面にて登録された項目をセット.<br/>
     * @param loanItemModel 項目設定画面から渡されたパラメータ(項目)
     */
    private void setItemSettigDataAttributeChange(LoanItemListModel loanItemModel) {
        for (LoanItemListModel itemRow : this.makingItemList) {
            if (itemRow.getItemName().equals(this.attributeChangeItemName)) {
                itemRow.setAttributeId(loanItemModel.getAttributeId());
                itemRow.setLength(loanItemModel.getLength());
                itemRow.setItemName(loanItemModel.getItemName());
                itemRow.setColumnName(loanItemModel.getColumnName());
                itemRow.setInputFormatId(loanItemModel.getInputFormatId());
                itemRow.setCellPosition(loanItemModel.getCellPosition());
                itemRow.setListSettingDivision(loanItemModel.getListSettingDivision());
                itemRow.setDefaultDivision(loanItemModel.getDefaultDivision());
                itemRow.setLinkDivision(loanItemModel.getLinkDivision());
                itemRow.setInitialId(loanItemModel.getInitialId());
                itemRow.setRequiredDivision(loanItemModel.getRequiredDivision());
                itemRow.setAttributeId(loanItemModel.getAttributeId());
                itemRow.setFixFlag(loanItemModel.getFixFlag());
                itemRow.setLoanSelectionV(loanItemModel.getLoanSelectionV());
                // NSD ADD START 2013/09/30 IDレングスをセット K.Takahashi
                itemRow.setIdLength(loanItemModel.getIdLength());
                // NSD ADD END   2013/09/30
                LoanFinancingUtils.getKoumokuSekkeiJyoukyou(itemRow, itemRow.getDefaultDivision()
                        , itemRow.getListSettingDivision(), itemRow.getLinkDivision());
                break;
            }
        }
    }

// 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START
    /**
     * 表示域切替処理(利用明細出力ラジオボタンの非同期処理)
     * @param selectedCode 選択中の利用明細出力ラジオボタンのコード値
     * @return 非同期で書き換えるエリアのHTML
     */
    public synchronized String doSelectRiyouMeisaiDivisionAsync(String selectedCode) {
        SfaArgumentUtil.assertNotEmpty(selectedCode);

        String html = "";
        this.riyouMeisaiOut = selectedCode;
        this.isRiyouMeisaiOut = this.riyouMeisaiOut.equals(LoanFinancingPlanningConstants.RIYOUMEISAI_OUT_NASI);

        try {
            FacesUtil.restoreView(super.getFacesContext(), EmbNavigationConstants.LOAN_FINANCING_PLANNING);

            html = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:riyouMeisaiGroup");

        } catch (Throwable t){
            super.handleAsynchronousException(t);
        }
        return html;
    }
    /**
     * 利用明細表示文言，受付票フッター表示文言について、ＤＢに格納する時の合計行数を求める．<br/>
     * @param mongon 利用明細表示文言，または受付票フッター表示文言
     * @param oneRowMaxMojisu １行の最大文字数
     * @return 利用明細表示文言，受付票フッター表示文言をＤＢに格納する時の合計行数
     */
    private static int calcTotalRowCount(String mongon, int oneRowMaxMojisu) {
        //＜仕様メモ（2010/03/24時点）＞
        //利用明細表示文言，受付票フッター表示文言は、文字列１行につき、ＤＢに１レコード作成する。
        //ユーザがＤＢの行のバイト数以上の文字数を１行に入力した場合、改行して２行として扱う。
        //
        //※例えば、ユーザが利用明細表示文言の１行に６０文字入力した場合、
        //「４０文字の行」と「２０文字の行」の計２行として扱うことになる。
        //このメソッドでは、各表示文言と１行の最大文字数から、ＤＢに格納することになる合計行数を求める。
        //
        //利用明細表示文言の１行の最大文字数：全角４０文字
        //受付票フッター表示文言の１行の最大文字数：全角７５文字


        //ＤＢに格納することになる合計行数
        int totalRowCnt = 0;

        //１行ごとの表示文言
        String[] mongonRows =  mongon.split(KAIGYO);

        for (String rowStr : mongonRows) {
            if ("".equals(rowStr)) {
                //行が空白（""）だった場合、それも１行とみなす
                totalRowCnt++;
            } else {
                //行数を計算する
                totalRowCnt += (int) Math.ceil((double) rowStr.length() / (double) oneRowMaxMojisu);
            }
        }

        return totalRowCnt;
    }

    /**
    * 利用明細表示文言，受付票フッター表示文言について、ＤＢに格納する時ためのモデルを作成する．<br/>
    * @param vec モデル格納用Vector
    * @param loanTableName モデルに格納するローンテーブル名
    * @param mongon 利用明細表示文言，または受付票フッター表示文言
    * @param oneRowMaxMojisu １行の最大文字数
    */
   private static void createLoanShowTextModel(
           Vector<LoanShowTextModel> vec
           , String loanTableName
           , String mongon
           , int oneRowMaxMojisu) {

       //画面入力された１行ごとの表示文言
       String[] mongonRows =  mongon.split(KAIGYO);

       //ＤＢに格納する１行の文字列
       String oneRowDbStr;
       //シーケンス番号（１から始まる）
       int seqNo = 1;

       //１行の文字列を区切る（substringする）回数
       int substrCnt = 0;
       //１行の文字列を区切る開始・終了位置
       int substrStartPos = 0;
       int substrEndPos = 0;

       for (String mongonRowStr : mongonRows) {

           if ("".equals(mongonRowStr)) {
               //画面入力された１行の表示文言が空白文字である場合、モデルを作成

               LoanShowTextModel model = new LoanShowTextModel();
               //ローンテーブル名
               model.setFieldLoanTableName(loanTableName);
               //シーケンス番号
               model.setFieldSEQ(String.valueOf(seqNo++));
               //１行分の文言（この場合は空白となる）
               model.setFieldText(mongonRowStr);

               vec.add(model);
               continue;
           }

           //画面入力された１行の表示文言が空白以外（文字数０以外）である場合、
           //表示文言を１行の最大文字数で区切ってモデルを作成する
           substrCnt = (int) Math.ceil((double) mongonRowStr.length() / (double) oneRowMaxMojisu);

           for (int i = 0; i < substrCnt; i++) {
               substrStartPos = oneRowMaxMojisu * i;
               substrEndPos = oneRowMaxMojisu * (i + 1);

               if (substrEndPos < mongonRowStr.length()) {
                   oneRowDbStr = mongonRowStr.substring(substrStartPos, substrEndPos);
               } else {
                   oneRowDbStr = mongonRowStr.substring(substrStartPos, mongonRowStr.length());
               }

               LoanShowTextModel model = new LoanShowTextModel();
               //ローンテーブル名
               model.setFieldLoanTableName(loanTableName);
               //シーケンス番号
               model.setFieldSEQ(String.valueOf(seqNo++));
               //１行分の文言
               model.setFieldText(oneRowDbStr);

               vec.add(model);
           }
       }
   }
// 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END
    /**
     * Id(サービス種別)取得.<br />
     * @return Id(サービス種別)
     */
    public String getId() {
        return id;
    }

    /**
     * Id(サービス種別)設定.<br />
     * @param id Id(サービス種別)
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 名称(ローンテーブル名)取得.<br />
     * @return 名称(ローンテーブル名)
     */
    public String getName() {
        return name;
    }

    /**
     * 名称(ローンテーブル名)設定.<br />
     * @param name 名称(ローンテーブル名)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 略称(ローンテーブル略称)取得.<br />
     * @return 略称(ローンテーブル略称)
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * 略称(ローンテーブル略称)設定.<br />
     * @param abbreviation 略称(ローンテーブル略称)
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * 選択ボタン押下フラグ取得.<br />
     * @return 選択ボタン押下フラグ
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * 選択ボタン押下フラグ設定.<br />
     * @param isSelected 選択ボタン押下フラグ
     */
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * 設計サービス種類取得.<br />
     * @return 設計サービス種類
     */
    public String getDesignServiceKind() {
        return designServiceKind;
    }

    /**
     * 設計サービス種類設定.<br />
     * @param designServiceKind 設計サービス種類
     */
    public void setDesignServiceKind(String designServiceKind) {
        this.designServiceKind = designServiceKind;
    }

    /**
     * 設計サービス種類ラジオ値(ローン受付)取得.<br />
     * @return 設計サービス種類ラジオ値(ローン受付)
     */
    public String getLoanuketsuke() {
        return LoanFinancingPlanningConstants.SERVICE_LOAN;
    }
    /**
     * 設計サービス種類ラジオ値(他サービス)取得.<br />
     * @return 設計サービス種類ラジオ値(他サービス)
     */
    public String getHokaservice() {
        return LoanFinancingPlanningConstants.SERVICE_OTHERS;
    }

    /**
     * 設計状況取得.<br />
     * @return 設計状況
     */
    public String getSekkeiJyoukyou() {
        return designSituation;
    }

    /**
     * 設計状況設定.<br />
     * @param situation 設計状況
     */
    public void setSekkeiJyoukyou(String situation) {
        this.designSituation = situation;
    }

    /**
     * 金利選択取得.<br />
     * @return 金利選択
     */
    public String getInterestRateSelectRadioValue() {
        return interestRateSelectRadioValue;
    }

    /**
     * 金利選択設定.<br />
     * @param interestRateSelectRadioValue 金利選択
     */
    public void setInterestRateSelectRadioValue(String interestRateSelectRadioValue) {
        this.interestRateSelectRadioValue = interestRateSelectRadioValue;
    }

    /**
     * 金利選択ラジオ値(金利)取得.<br />
     * @return 金利選択ラジオ値(金利)
     */
    public String getInterestRateValue() {
        return LoanFinancingPlanningConstants.INTEREST_RATE;
    }

    /**
     * 金利選択ラジオ値(年間返済額)取得.<br />
     * @return 金利選択ラジオ値(年間返済額)
     */
    public String getYearRepaymentValue() {
        return LoanFinancingPlanningConstants.YEAR_REPAMENT;
    }

    /**
     * 作成項目リスト取得.<br />
     * @return 作成項目リスト
     */
    public List<LoanItemListModel> getMakingItemList() {
        return makingItemList;
    }

    /**
     * 作成項目リスト設定.<br />
     * @param makingItemList 作成項目リスト
     */
    public void setMakingItemList(List<LoanItemListModel> makingItemList) {
        this.makingItemList = makingItemList;
    }

    /**
     * 選択可能項目リスト取得.<br />
     * @return 選択可能項目リスト
     */
    public List<LoanItemListModel> getSelectableItemList() {
        return selectableItemList;
    }

    /**
     * 選択可能項目リスト設定.<br />
     * @param selectableItemList 選択可能項目リスト
     */
    public void setSelectableItemList(List<LoanItemListModel> selectableItemList) {
        this.selectableItemList = selectableItemList;
    }

    /**
     * 選択可能項目コンボ用リスト取得.<br />
     * @return 選択可能項目コンボ用リスト
     */
    public List<SelectItem> getSelectableServiceList() {
        return selectableServiceList;
    }

    /**
     * 選択可能項目コンボ用リスト設定.<br />
     * @param selectableServiceList 選択可能項目コンボ用リスト
     */
    public void setSelectableServiceList(List<SelectItem> selectableServiceList) {
        this.selectableServiceList = selectableServiceList;
    }

    /**
     * Bookコンボ用リスト取得.<br />
     * @return Bookコンボ用リスト
     */
    public List<SelectItem> getSelectBooKList() {
        return selectBooKList;
    }

    /**
     * Bookコンボ用リスト設定.<br />
     * @param selectBooKList Bookコンボ用リスト
     */
    public void setSelectBooKList(List<SelectItem> selectBooKList) {
        this.selectBooKList = selectBooKList;
    }

    /**
     * 申込み時の保証会社コンボ用リスト取得.<br />
     * @return 申込み時の保証会社コンボ用リスト
     */
    public List<SelectItem> getSelectGuarantyCompanyList() {
        return selectGuarantyCompanyList;
    }

    /**
     * 申込み時の保証会社コンボ用リスト設定.<br />
     * @param selectGuarantyCompanyList 申込み時の保証会社コンボ用リスト
     */
    public void setSelectGuarantyCompanyList(List<SelectItem> selectGuarantyCompanyList) {
        this.selectGuarantyCompanyList = selectGuarantyCompanyList;
    }

    /**
     * Sheetコンボ用リスト取得.<br />
     * @return Sheetコンボ用リスト
     */
    public List<SelectItem> getSelectSheetList() {
        return selectSheetList;
    }

    /**
     * Sheetコンボ用リスト設定.<br />
     * @param selectSheetList Sheetコンボ用リスト
     */
    public void setSelectSheetList(List<SelectItem> selectSheetList) {
        this.selectSheetList = selectSheetList;
    }

    /**
     * Bookコンボ選択値取得.<br />
     * @return Bookコンボ選択値
     */
    public String getBook() {
        return book;
    }

    /**
     * Bookコンボ選択値設定.<br />
     * @param book Bookコンボ選択値
     */
    public void setBook(String book) {
        this.book = book;
    }

    /**
     * 申込み時の保証会社コンボ選択値取得.<br />
     * @return 申込み時の保証会社コンボ選択値
     */
    public String getGuarantyCompany() {
        return guarantyCompany;
    }

    /**
     * 申込み時の保証会社コンボ選択値設定.<br />
     * @param guarantyCompany 申込み時の保証会社コンボ選択値
     */
    public void setGuarantyCompany(String guarantyCompany) {
        this.guarantyCompany = guarantyCompany;
    }

    /**
     * Sheetコンボ選択値取得.<br />
     * @return Sheetコンボ選択値
     */
    public String getSheet() {
        return sheet;
    }

    /**
     * Sheetコンボ選択値設定.<br />
     * @param sheet Sheetコンボ選択値
     */
    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    /**
     * 選択可能項目コンボ選択値取得.<br />
     * @return 選択可能項目コンボ選択値
     */
    public String getSelectedService() {
        return selectedService;
    }

    /**
     * 選択可能項目コンボ選択値設定.<br />
     * @param selectedService 選択可能項目コンボ選択値
     */
    public void setSelectedService(String selectedService) {
        this.selectedService = selectedService;
    }

    /**
     * 金利（小数部）取得.<br />
     * @return 金利（小数部）
     */
    public String getInterestRateDecimal() {
        return interestRateDecimal;
    }

    /**
     * 金利（小数部）設定.<br />
     * @param interestRateDecimal 金利（小数部）
     */
    public void setInterestRateDecimal(String interestRateDecimal) {
        this.interestRateDecimal = interestRateDecimal;
    }

    /**
     * 金利（整数部）設定.<br />
     * @param interestRateInt 金利（整数部）
     */
    public void setInterestRateInt(String interestRateInt) {
        this.interestRateInt = interestRateInt;
    }

    /**
     * 金利（整数部）取得.<br />
     * @return 金利（整数部）
     */
    public String getInterestRateInt() {
        return interestRateInt;
    }

    /**
     * 年間返済額取得.<br />
     * @return 年間返済額
     */
    public String getYearRepayment() {
        return yearRepayment;
    }

    /**
     * 年間返済額設定.<br />
     * @param yearRepayment 年間返済額
     */
    public void setYearRepayment(String yearRepayment) {
        this.yearRepayment = yearRepayment;
    }

    /**
     * 設計状況(新規)フラグ取得.<br />
     * @return 設計状況(新規)フラグ
     */
    public boolean isNew() {
        return this.designSituation.equals(LoanFinancingPlanningConstants.DesignSituation.NEW.code());
    }

    /**
     * 設計状況(設計中)フラグ取得.<br />
     * @return 設計状況(設計中)フラグ
     */
    public boolean isDesigning() {
        return this.designSituation.equals(LoanFinancingPlanningConstants.DesignSituation.DESIGNING.code());
    }

    /**
     * 設計状況(サービス中)フラグ取得.<br />
     * @return 設計状況(サービス中)フラグ
     */
    public boolean isServing() {
        return this.designSituation.equals(LoanFinancingPlanningConstants.DesignSituation.SERVING.code());
    }

    /**
     * 設計状況(サービス終了)フラグ取得.<br />
     * @return 設計状況(サービス終了)フラグ
     */
    public boolean isServiceEnd() {
        return this.designSituation.equals(LoanFinancingPlanningConstants.DesignSituation.SERVICE_END.code());
    }

    /**
     * 設計サービス種類ラジオでローンを選択しているか取得.<br />
     * @return 設計サービス種類ラジオでローンを選択しているか
     */
    public boolean isKinri() {
        return isKinri;
    }

    /**
     * 金利選択種類ラジオで金利を選択しているか取得.<br />
     * @return 金利選択種類ラジオで金利を選択しているか
     */
    public boolean isLoan() {
        return isLoan;
    }

    /**
     * 登録済みか取得.<br />
     * @return 登録済みか
     */
    public boolean isRegisted() {
        return isRegisted;
    }
    // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）START
    /**
     * 利用明細出力ラジオで無しを選択しているか取得.<br />
     * @return 利用明細出力ラジオで無しを選択しているか
     */
    public boolean isRiyouMeisai() {
        return isRiyouMeisaiOut;
    }

    /**
     * 利用明細出力(有りor無し)を取得します。
     * @return 利用明細出力(有りor無し)
     */
    public String getRiyouMeisaiOut() {
        return riyouMeisaiOut;
    }

    /**
     * 利用明細出力(有りor無し)を設定します。
     * @param riyouMeisaiOut 利用明細出力(有りor無し)
     */
    public void setRiyouMeisaiOut(String riyouMeisaiOut) {
        this.riyouMeisaiOut = riyouMeisaiOut;
    }

    /**
     * 意思確認(有りor無し)を取得します。
     * @return 意思確認(有りor無し)
     */
    public String getIsiKakunin() {
        return isiKakunin;
    }

    /**
     * 意思確認(有りor無し)を設定します。
     * @param isiKakunin 意思確認(有りor無し)
     */
    public void setIsiKakunin(String isiKakunin) {
        this.isiKakunin = isiKakunin;
    }

    /**
     * 利用明細表示文言を取得します。
     * @return 利用明細表示文言
     */
    public String getRiyouMeisaiHyojiMongon() {
        return riyouMeisaiHyojiMongon;
    }

    /**
     * 利用明細表示文言を設定します。
     * @param riyouMeisaiHyojiMongon 利用明細表示文言
     */
    public void setRiyouMeisaiHyojiMongon(String riyouMeisaiHyojiMongon) {
        this.riyouMeisaiHyojiMongon = riyouMeisaiHyojiMongon;
    }

    /**
     * 利用明細出力ラジオ(有り)取得.<br />
     * @return 利用明細出力ラジオ値(有り)
     */
    public String getRiyouMeisaiOutAri() {
        return LoanFinancingPlanningConstants.RIYOUMEISAI_OUT_ARI;
    }

    /**
     * 利用明細出力ラジオ(無し)取得.<br />
     * @return 利用明細出力ラジオ値(無し)
     */
    public String getRiyouMeisaiOutNasi() {
        return LoanFinancingPlanningConstants.RIYOUMEISAI_OUT_NASI;
    }

    /**
     * 受付区分ラジオ(会員)取得.<br />
     * @return  受付区分ラジオ値(会員)
     */
    public String getUketukeKbnKaiin() {
        return LoanFinancingPlanningConstants.UKETUKE_KBN_KAIIN;
    }

    /**
     * 受付区分ラジオ(非会員)取得.<br />
     * @return  受付区分ラジオ値(非会員)
     */
    public String getUketukeKbnHiKaiin() {
        return LoanFinancingPlanningConstants.UKETUKE_KBN_HIKAIIN;
    }

    /**
     * 受付区分ラジオ(両方)取得.<br />
     * @return  受付区分ラジオ値(両方)
     */
    public String getUketukeKbnBoth() {
        return LoanFinancingPlanningConstants.UKETUKE_KBN_BOTH;
    }

    /**
     * 意思確認ラジオ(有り)取得.<br />
     * @return  意思確認ラジオ値(有り)
     */
    public String getIsiKakuninAri() {
        return LoanFinancingPlanningConstants.ISI_KAKUNIN_ARI;
    }

    /**
     * 意思確認ラジオ(有り)取得.<br />
     * @return  意思確認ラジオ値(有り)
     */
    public String getIsiKakuninNasi() {
        return LoanFinancingPlanningConstants.ISI_KAKUNIN_NASI;
    }
    // 2013/11/19 青森銀行から取込 ローン取引対応（意思確認機能）END
}
