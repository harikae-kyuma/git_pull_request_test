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
 * ���[���e��\���݌v(�݌v)
 * �쐬�� : (2008/07/03)
 * @author: I.suzuki
 */
@SuppressWarnings("serial")
public class LoanFinancingPlanningBean extends EmbFacesBean {

    /** Id(�T�[�r�X���) */
    private String id;

    /** ����(���[���e�[�u����) */
    private String name;

    /** ����(���[���e�[�u������) */
    private String abbreviation;

    /** �݌v�T�[�r�X���(���[��or���T�[�r�X) */
    private String designServiceKind;

    /** �݌v�� */
    private String designSituation;

    /** �݌v�\���ڃR���{�ɂđI�����ꂽ�T�[�r�X��Id(�T�[�r�X���) */
    private String selectedId;

    /** �݌v�\���ڃR���{�ɂđI�����ꂽ�T�[�r�X�̖���(���[���e�[�u����) */
    private String selectedName;

    /** �݌v�\���ڃR���{�ɂđI�����ꂽ�T�[�r�X�̐݌v�� */
    private String selectedDesignSituation;

    /** �����I�����W�I�̒l */
    private String interestRateSelectRadioValue;

    /** ����(������) */
    private String interestRateInt;

    /** ����(������) */
    private String interestRateDecimal;

    /** �N�ԕԍϊz */
    private String yearRepayment;

    /** �\�����̕ۏ؉�� */
    private String guarantyCompany;

    /** BooK */
    private String book;

    /** Sheet */
    private String sheet;

    /** �I���{�^�������t���O */
    private boolean isSelected;

    /** �I���\���ڃR���{�őI�����ꂽ���� */ 
    private String selectedService;

    /** �\�����̕ۏ؉�ЃR���{ */
    private List<SelectItem> selectGuarantyCompanyList;

    /** Book�R���{�ݒ�p���X�g */
    private List<FileNameCommonModel> bookList = new ArrayList<FileNameCommonModel>();

    /** Sheet�R���{�ݒ�p���X�g */
    private List<SheetNameCommonModel> sheetList = new ArrayList<SheetNameCommonModel>();

    /** BooK�R���{ */
    private List<SelectItem> selectBooKList;

    /** Sheet�R���{ */
    private List<SelectItem> selectSheetList;

    /** �I���\���ڃR���{ */
    private List<SelectItem> selectableServiceList = new ArrayList<SelectItem>();

    /** �݌v�T�[�r�X��ރ��W�I�Ń��[����I�����Ă��邩 */
    private boolean isLoan;

    /** �����I����ރ��W�I�ŋ�����I�����Ă��邩 */
    private boolean isKinri;

    /** �����ύX�{�^���������̍��ږ� */
    private String attributeChangeItemName;

    /** �����ύX�{�^�������t���O */
    private boolean isAttributeChange;

    /** �o�^�ς݃t���O */
    private boolean isRegisted;

    /** �I���\���ڃ��X�g */
    private List<LoanItemListModel> selectableItemList;

    /** �쐬���ڃ��X�g�Z�b�g�p���X�g */
    private List tempMakingItemList;

    /** �쐬���ڃ��X�g */
    private List<LoanItemListModel> makingItemList;

    // �ǉ����ڂ��𔻒f����ׂ̏������ޔ�p
    private List<LoanItemListModel> makingItemListBackUpInit;

    // �݌v�T�[�r�X��ރ��W�I�{�^���ؑ֎��̑ޔ�p
    /** �쐬���ڃ��X�g�o�b�N�A�b�v(���[��) */
    private List<LoanItemListModel> makingItemListBackUpLoan;

    /** �쐬���ڃ��X�g�o�b�N�A�b�v(���̑��T�[�r�X) */
    private List<LoanItemListModel> makingItemListBackUpOthers;

    /** ���[���݌v��ʋ��ʏ�񃂃f�� */
    private LoanFinancingPlanningInfoModel loanInfo;

    /** �I�����[���̓o�^�����i�r������p�j */
    private String selectedLoanRegistDay;

    // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART
    /** ���p���׏o�̓��W�I�ŗL���I�����Ă��邩 */
    private boolean isRiyouMeisaiOut;

    /** ���p���׏o��(�L��or����) */
    private String riyouMeisaiOut;

    /** �ӎv�m�F(�L��or����) */
    private String isiKakunin;

    /** ���p���ו\������ */
    private String riyouMeisaiHyojiMongon;

    /** ���s�i�o�́j */
    private static final String KAIGYO  = "\r\n";

    /** ���p���ו\�������̍ő�s�� */
    private static final int MAX_ROW_RIYOU_MEISAI_HYOJI_MONGON = 5;

    /** ���p���ו\�������̂P�s�̍ő啶�����i�S�p�j */
    private static final int MAX_MOJISU_ONE_ROW_MEISAI_HYOJI_MONGON = 40;

    /** ��t�[�t�b�^�[�\�������̍ő�s�� */
    private static final int MAX_ROW_UKETUKE_FOOTER_HYOJI_MONGON = 10;

    /** ��t�[�t�b�^�[�\�������̂P�s�̍ő啶�����i�S�p�j */
    private static final int MAX_MOJISU_ONE_ROW_UKETUKE_FOOTER_HYOJI_MONGON = 75;

    /** ���p���׏o��(�L��or����) �����\���� */
    private String initRiyouMeisaiOut;
    // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND

    /** �����������̍ő�o�C�g�� */
    private static final int MAX_BYTE_INTEREST_RATE_DECIMAL = 5;

    /**
     * ��������.<br/>
     * @param obj �������p�����[�^
     */
    public void doInitialize(Object obj) {
        // �p�����[�^�̎擾
        LoanFinancingPlanningBeanParams params = (LoanFinancingPlanningBeanParams) obj;

        this.loanInfo = (LoanFinancingPlanningInfoModel) EmbStateHandler.get("LOAN_INFO_KEY", Scope.SESSION);

        // ������
        this.initializeFieldsCommon();

        if (params.isSelected()) {
            this.initializeFieldsSelected(params);
        } else {
            this.initializeFieldsNew(params);
        }

        this.initializeFieldsCommonCombo();

        // �݌v�󋵁u�݌v���v���u�T�[�r�X���v�̂Ƃ��A�����\������Item���o�b�N�A�b�v
        if (this.isDesigning() || this.isServing()) {
            this.makingItemListBackUpInit = (List<LoanItemListModel>) ObjectUtil.deepClone(this.makingItemList);
        }
    }

    /**
     * �o�^����.<br/>
     * @return ���[���e��\���݌v(�T�[�r�X�I��)�_����
     */
    public String doRegist() {

        // ���̓`�F�b�N
        verifyRequiredOrDisplayConfirmation();

        // �o�^����
        executeRegistServise();

        super.chainBeanAction("loanFinancingPlanningSelectServiceBean.doInitialize");

        return EmbNavigationConstants.LOAN_FINANCING_PLANNING_SELECT_SERVICE;
    }

    /**
     * �T�[�r�X�J�n����.<br/>
     * @return ���[���e��݌v(�݌v)��ʂ̘_����
     */
    public String doStartService() {

        // �T�[�r�X�J�n����
        executeStartService();

        this.designSituation = LoanFinancingPlanningConstants.DesignSituation.SERVING.code();

        addFacesMessage(EmbMessageIdConstants.INFO_START_SERVICE);

        return EmbNavigationConstants.LOAN_FINANCING_PLANNING;
    }

    /**
     * �T�[�r�X�I������.<br/>
     * @return ���[���e��݌v(�݌v)��ʂ̘_����
     */
    public String doEndService() {

        // �T�[�r�X�J�n����
        executeStartService();

        this.designSituation = LoanFinancingPlanningConstants.DesignSituation.SERVICE_END.code();

        addFacesMessage(EmbMessageIdConstants.INFO_END_SERVICE);

        return EmbNavigationConstants.LOAN_FINANCING_PLANNING;
    }

    /**
     * ��ʑJ��(��ʊm�F).<br/>
     * @return ���[���e��\���݌v(��ʊm�F)�_����
     */
    public String doTransferDisplayConfirmation() {

        verifyRequiredOrDisplayConfirmation();

        LoanFinancingPlanningDisplayConfirmationBeanParams params =
            new LoanFinancingPlanningDisplayConfirmationBeanParams();
        params.setLoanTableName(this.name);
        params.setLoanItemList(this.makingItemList);
        params.setBook(this.book);
        params.setSheet(this.sheet);

        super.chainBeanAction("loanFinancingPlanningDisplayConfirmationBean.doInitialize", params); // �J�ڐ��ʂ̏�������

        super.setBackwardViewId(EmbNavigationConstants.LOAN_FINANCING_PLANNING);  // �J�ڐ��ʂ̖߂�{�^���������ɕ\��������

        return EmbNavigationConstants.LOAN_FINANCING_PLANNING_DISPLAY_CONFIRMATION;
    }

    /**
     * ��ʑJ��(���ڐݒ�(�����ύX�{�^��������)).<br/>
     * @return ���[���e��\���݌v(���ڐݒ�)�_����
     */
    public String doTransferItemSettingAttributeChange() {
        LoanItemListModel model = (LoanItemListModel) super.getRequestManagedBean("makingItemRow");

        LoanFinancingPlanningItemSettingBeanParams params = new LoanFinancingPlanningItemSettingBeanParams();
        params.setLoanItemListModel((LoanItemListModel) ObjectUtil.deepClone(model));

        super.chainBeanAction("loanFinancingPlanningItemSettingBean.doInitializeAttributeChange", params); // �J�ڐ��ʂ̏�������

        super.setBackwardViewId(EmbNavigationConstants.LOAN_FINANCING_PLANNING);  // �J�ڐ��ʂ̖߂�{�^���������ɕ\��������

        return EmbNavigationConstants.LOAN_FINANCING_PLANNING_ITEM_SETTING;
    }

    /**
     * ��ʑJ��(���ڐݒ�(���ڒǉ��{�^��������)).<br/>
     * @return ���[���e��\���݌v(���ڐݒ�)�_����
     */
    public String doTransferItemSettingNew() {

        super.chainBeanAction("loanFinancingPlanningItemSettingBean.doInitializeItemAdd"); // �J�ڐ��ʂ̏�������

        super.setBackwardViewId(EmbNavigationConstants.LOAN_FINANCING_PLANNING);  // �J�ڐ��ʂ̖߂�{�^���������ɕ\��������

        return EmbNavigationConstants.LOAN_FINANCING_PLANNING_ITEM_SETTING;
    }

    /**
     * ���ڐݒ��ʂɂēo�^���ꂽ���ڂ��Z�b�g.<br/>
     * @param obj ���ڐݒ��ʂ���n���ꂽ�p�����[�^
     */
    public void doSetItemSettingData(Object obj) {
        // �p�����[�^�̎擾
        LoanFinancingPlanningBeanParams params = (LoanFinancingPlanningBeanParams) obj;

        LoanItemListModel loanItemModel = params.getLoanItemListModel();
        this.attributeChangeItemName = params.getAttributeChangeItemName();
        this.isAttributeChange = StringUtils.isNotEmpty(this.attributeChangeItemName);

        // �p�����[�^�`�F�b�N
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
        // �T�[�r�X�J�n�{�^�����g�p�s�ɂ���
        this.isRegisted = false;

        // �o�^��A��ʂ�������Ԃŕ\��
        super.chainBeanAction("loanFinancingPlanningItemSettingBean.doInitializeItemAdd");
    }

    /**
     * �\����ؑ֏���(�݌v�T�[�r�X��ރ��W�I�{�^���̔񓯊�����)
     * @param selectedCode �I�𒆂̐݌v�T�[�r�X��ރ��W�I�{�^���̃R�[�h�l
     * @param selectedAcceptKbn �I�𒆂̎�t�敪���W�I�{�^���̃R�[�h�l
     * @param selectedIsiKakunin �I�𒆂̈ӎv�m�F���W�I�{�^���̃R�[�h�l
     * @return �񓯊��ŏ���������G���A��HTML
     */
// 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART
//  public synchronized String[] doSelectServiceDivisionAsync(String selectedCode) {
    public synchronized String[] doSelectServiceDivisionAsync(
            String selectedCode, String selectedAcceptKbn, String selectedIsiKakunin) {
// 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND
        SfaArgumentUtil.assertNotEmpty(selectedCode);

        String[] html = null;

        this.designServiceKind = selectedCode;

        this.isLoan = this.designServiceKind.equals(LoanFinancingPlanningConstants.SERVICE_LOAN);
        if (this.isLoan) {
            this.makingItemListBackUpOthers = (List<LoanItemListModel>) ObjectUtil.deepClone(this.makingItemList);
            this.makingItemList = this.makingItemListBackUpLoan;
            // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART
            //�ӎv�m�F��ێ�����
            this.isiKakunin = selectedIsiKakunin;
            // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND
        } else {
            this.makingItemListBackUpLoan = (List<LoanItemListModel>) ObjectUtil.deepClone(this.makingItemList);
            this.makingItemList = this.makingItemListBackUpOthers;
            // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART

            //�ӎv�m�F���u�L��v�ɐݒ肷��
            this.isiKakunin = LoanFinancingPlanningConstants.ISI_KAKUNIN_ARI;
            // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND
        }

        try {
            // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART
            //html = new String[2];
            html = new String[3];
           // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND

            FacesUtil.restoreView(super.getFacesContext(), EmbNavigationConstants.LOAN_FINANCING_PLANNING);

            html[0] = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:hosyouGaisyaGroup");
            html[1] = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:sakuseiItemListGroup");
            // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART
            html[2] = FacesUtil.getHtml(super.getFacesContext(), "loanFinancingPlanning:isiKakuninGrid");
            // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND

        } catch (Throwable t){
            super.handleAsynchronousException(t);
        }
        return html;
    }

    /**
     * �\����ؑ֏���(Book�R���{�̔񓯊�����)
     * @param selectedCode �I�𒆂�Book�R���{�̒l
     * @return �񓯊��ŏ���������G���A��HTML
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
     * �\����ؑ֏���(�����I�����W�I�{�^���̔񓯊�����)
     * @param selectedCode �I�𒆂̋����I�����W�I�{�^���̃R�[�h�l
     * @return �񓯊��ŏ���������G���A��HTML
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
     * �\����ؑ֏���(�I���\���ڃR���{�񓯊�����)
     * @param selectedServiceId �I�𒆂̑I���\���ڃR���{�̃R�[�h�l
     * @return �񓯊��ŏ���������G���A��HTML
     */
    public synchronized String doSelectListDivisionAsync(String selectedServiceId) {
        String html = "";
        this.selectedService = selectedServiceId;

        try {
            // ���̓`�F�b�N
            verifyRequiredSeletableService(this.selectedService);

            List <LoanSekkeiCommonModel> sekkeiList = this.loanInfo.getLoanSekkeiV();

            for (LoanSekkeiCommonModel sekkeiModel : sekkeiList) {
                if (this.selectedService.equals(sekkeiModel.getLoanTableName())) {
                    this.selectedId = sekkeiModel.getServiceSyubetsu();             // �T�[�r�X���
                    this.selectedName = this.selectedService;                      // ����
                    this.selectedDesignSituation = sekkeiModel.getSekkeiJyokyo();   // �݌v��
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
     * �I���s��ʃe�[�u���֒ǉ�.(�񓯊�����)
     * @param asyncSelectedId �I���s��ID
     * @return ����������HTML
     */
    public synchronized String[] doAddSelectionTableAsync(String asyncSelectedId) {
        // �����̋�`�F�b�N
        SfaArgumentUtil.assertNotEmpty(asyncSelectedId);
        String[] html = null;

        try {
            html = new String[2];

            // �I���s��ID��莩�ꗗ����Ώۂ̈ꗗ�փf�[�^���ړ�
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
     * �I���s���폜.(�񓯊�����)
     * @param asyncSelectedId �I���s��ID
     * @return ����������HTML
     */
    public synchronized String[] doDeleteSelectionTableAsync(String asyncSelectedId) {
        // �����̋�`�F�b�N
        SfaArgumentUtil.assertNotEmpty(asyncSelectedId);

        String[] html = null;

        try {
            html = new String[2];

            // �I���s��ID��莩�ꗗ����Ώۂ̈ꗗ�փf�[�^���ړ�
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
     * �ꗗ�̑I���s����ֈړ�(�񓯊�����).<br/>
     * @param asyncSelectedId �I���s��ID
     * @return �񓯊��ŏ���������G���A��HTML
     */
    public synchronized String doUpAsync(String asyncSelectedId) {
        // �����̋�`�F�b�N
        SfaArgumentUtil.assertNotEmpty(asyncSelectedId);

        String html = "";

        try {
            // �ꗗ�̑I���s�̏㉺�����ւ���
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
     * �ꗗ�̑I���s�̉��ړ�(�񓯊�����).<br/>
     * @param asyncSelectedId �I���s��ID
     * @return �񓯊��ŏ���������G���A��HTML
     */
    public synchronized String doDownAsync(String asyncSelectedId) {
        SfaArgumentUtil.assertNotEmpty(asyncSelectedId);

        String html = "";
        try {
            // �ꗗ�̑I���s�̏㉺�����ւ���
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
     * ���͂ɕύX���������Ƃ��A�T�[�r�X�J�n�{�^�����g�p�s�Ƃ���.<br/>
     * @return �񓯊��ŏ���������G���A��HTML
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
     * ���ʊe�t�B�[���h�̏�����.<br/>
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
        // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART
        this.riyouMeisaiOut = LoanFinancingPlanningConstants.RIYOUMEISAI_OUT_ARI;
        this.isiKakunin = LoanFinancingPlanningConstants.ISI_KAKUNIN_ARI;
        this.riyouMeisaiHyojiMongon = "";
        this.initRiyouMeisaiOut = "";
        // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND
    }

    /**
     * ���ʊe�t�B�[���h�̏�����(�R���{).<br/>
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
     * �V�[�g���X�g��ݒ�.<br/>
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
     * �e�t�B�[���h�̏�����(�V�K�ȊO).<br/>
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
        // ���������������ɒl���L��ꍇ
        if (interestRateList.length == 2) {
            // �E�[�����߂��s�����l���Z�b�g
            this.interestRateDecimal = StringFormat.fillZeroToRight(
                    interestRateList[1], MAX_BYTE_INTEREST_RATE_DECIMAL);
        // �������ɒl�������ꍇ(�N�ԕԍϊz�I��)
        } else if (this.interestRateInt.equals("")){
            this.interestRateDecimal = "";
        // �������ɒl���L��A�������ɒl�������ꍇ(�o�^�l�������̃f�[�^)
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
        // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART
        //���p���׏o��
        this.riyouMeisaiOut = params.getLoanSekkeiModel().getFieldDetailOutput();

        // ��ʏ����\�����̗��p���׏o�͂̑I��l��ێ�
        this.initRiyouMeisaiOut = params.getLoanSekkeiModel().getFieldDetailOutput();

        this.isRiyouMeisaiOut = this.riyouMeisaiOut.equals(LoanFinancingPlanningConstants.RIYOUMEISAI_OUT_NASI);

        //�ӎv�m�F
        this.isiKakunin = params.getLoanSekkeiModel().getFieldRepetitionUse();

        //���p���ו\������
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

        // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND
    }

    /**
     * �e�t�B�[���h�̏�����(�V�K).<br/>
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
        // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART
        this.isRiyouMeisaiOut = false;
        // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND
        this.makingItemList = setItemListFromFixModel(this.tempMakingItemList);
        this.makingItemListBackUpInit = new ArrayList<LoanItemListModel>();
        this.makingItemListBackUpLoan = new ArrayList<LoanItemListModel>();
        this.makingItemListBackUpOthers = new ArrayList<LoanItemListModel>();
        // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART
        //���p���׏o��
        this.riyouMeisaiOut = LoanFinancingPlanningConstants.RIYOUMEISAI_OUT_ARI;

        //�ӎv�m�F
        this.isiKakunin = LoanFinancingPlanningConstants.ISI_KAKUNIN_ARI;

        //���p���ו\������
        this.riyouMeisaiHyojiMongon = "";

        // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND
    }


    /**
     * �����T�[�r�X�I���E�ݒ菈��.<br/>
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


        // TB�Ɩ����ʗv�������Z�b�g
        request.setTbGyomuCommonReqM(LoanFinancingUtils.getTbGyoumuCommonRequest());

        LoanExistSelectResponseModel response =
            (LoanExistSelectResponseModel) super.invoke(ServiceProxyType.DOMAIN_SERVICE,
                    EmbDomainServiceNameConstants.LOAN_EXIST_SELECT_DOMAIN_INTERFACE , request);

        List<LoanItemListModel> tempList = new ArrayList<LoanItemListModel>();
        this.selectableItemList = LoanFinancingUtils.setItemListFromItemModel(
                tempList, response.getLoanSetM().getLoanItemV(), this.loanInfo.getLinkItemV());
    }

    /**
     * �o�^����.<br/>
     */
    private void executeRegistServise() {
        LoanRegistRequestModel  request = new LoanRegistRequestModel();

        LoanSetCommonModel setModel = new LoanSetCommonModel();

        setModel.setLoanSekkeiM(getParamLoanSekkeiModel());

        Vector itemList = new Vector();
        setItemModelFromItemList(itemList);
        setModel.setLoanItemV(itemList);

        request.setLoanSetM(setModel);

        // TB�Ɩ����ʗv�������Z�b�g
        request.setTbGyomuCommonReqM(LoanFinancingUtils.getTbGyoumuCommonRequest());

        // 2010.06.25 �X�Ή��@�V�K�o�^�t���O��ǉ�
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
     * ���[���݌v�p�����[�^���擾.<br/>
     * @return ���[���݌v�p�����[�^
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
        // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART
        //��t�敪
        sekkeiModel.setFieldReceiptDivision("0");
        //���p���׏o��
        sekkeiModel.setFieldDetailOutput(this.riyouMeisaiOut);
        //�ӎv�m�F
        sekkeiModel.setFieldRepetitionUse(this.isiKakunin);
        //���p���ו\�������i���s�R�[�h��split����Model�Ɋi�[����j
        if (!"".equals(this.riyouMeisaiHyojiMongon)) {
            Vector<LoanShowTextModel> fieldDetailTextV = sekkeiModel.getFieldDetailTextV();

            createLoanShowTextModel(
                    fieldDetailTextV
                    , this.name
                    , this.riyouMeisaiHyojiMongon
                    , MAX_MOJISU_ONE_ROW_MEISAI_HYOJI_MONGON);

        }
        // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND

        return sekkeiModel;
    }

    /**
     * �T�[�r�X�J�n/�I������.<br/>
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
        // NSD ADD START 2013/04/05 �T�[�r�X�J�n�E�I�����Ɏ����ɂ��r������ǉ�
        model.setRegistDay(this.selectedLoanRegistDay);
        // NSD ADD END   2013/04/05
        if (this.isDesigning()) {
            model.setSekkeiJyokyo(LoanFinancingPlanningConstants.DesignSituation.SERVING.code());
        } else {
            model.setSekkeiJyokyo(LoanFinancingPlanningConstants.DesignSituation.SERVICE_END.code());
        }
        // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART
        model.setFieldDetailOutput(this.initRiyouMeisaiOut);
        // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND
        request.setLoanSekkeiM(model);

        // TB�Ɩ����ʗv�������Z�b�g
        request.setTbGyomuCommonReqM(LoanFinancingUtils.getTbGyoumuCommonRequest());

        super.invoke(ServiceProxyType.DOMAIN_SERVICE,
                EmbDomainServiceNameConstants.LOAN_START_DOMAIN_INTERFACE, request);
    }

    /**
     * ���̓`�F�b�N(�o�^�E�o�̓{�^���������̕K�{�`�F�b�N).<br/>
     */
    private void verifyRequiredOrDisplayConfirmation() {
        if (this.isLoan) {
            // �\�����̕ۏ؉�Ђ̕K�{�`�F�b�N
            SfaValidationUtil.validateRequiredSelect(this.guarantyCompany, ItemName.get("moushikomijinohosyougaisya"));
        }
        // Book�̕K�{�`�F�b�N
        SfaValidationUtil.validateRequiredSelect(this.book, ItemName.get("book"));
        // Sheet�̕K�{�`�F�b�N
        SfaValidationUtil.validateRequiredSelect(this.sheet, ItemName.get("sheet"));
        // �����I���̕K�{�`�F�b�N
        SfaValidationUtil.validateRequiredSelect(this.interestRateSelectRadioValue, ItemName.get("kinrisentaku"));
        if (this.isLoan) {
            if (this.isKinri) {
                // �����i�������j�̕K�{�`�F�b�N
                SfaValidationUtil.validateRequiredInput(
                        this.interestRateInt, ItemName.get("kinri") + ItemName.get("seisuubu"));
                // �����i�������j�̕K�{�`�F�b�N
                SfaValidationUtil.validateRequiredInput(
                        this.interestRateDecimal, ItemName.get("kinri") + ItemName.get("syousuubu"));
                // �G���[������ꍇ�A���b�Z�[�W���o��
                super.handleValidatorException();
                // ������0�����傫���l�ł��邩�̃`�F�b�N
                SfaValidationUtil.validateNumberExcludeMinimum(
                        this.interestRateInt + "." + this.interestRateDecimal, "0", ItemName.get("kinri"));
            } else {
                // �N�ԕԍϊz�̕K�{�`�F�b�N
                SfaValidationUtil.validateRequiredInput(this.yearRepayment, ItemName.get("nenkanhensaigaku"));
                // �N�ԕԍϊz��0�����傫���l�ł��邩�̃`�F�b�N
                SfaValidationUtil.validateNumberExcludeMinimum(
                        this.yearRepayment, "0", ItemName.get("nenkanhensaigaku"));
            }
        }

        // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART
        // ���s�R�[�h���܂߂Ȃ����p���ו\������
        String tmpAllMeisaiMongon = this.riyouMeisaiHyojiMongon.replace(KAIGYO, "");

        // ���p���ו\���������c�a�Ɋi�[����ۂ̍��v�s��
        int riyouMeisaiTotalRowCnt;

        // ��t�[�t�b�^�[�\���������c�a�Ɋi�[����ۂ̍��v�s��
        int footerTotalRowCnt;

        // ���p���ו\�������̑S�p�`�F�b�N
        SfaValidationUtil.validateDoubleByte(tmpAllMeisaiMongon, ItemName.get("riyoumeisaihyoujimongon"));

        // ���p���ו\�������̃o�C�g������`�F�b�N�i400�o�C�g�𒴂���ƃG���[�j
        if(StringUtils.isNotBlank(this.riyouMeisaiHyojiMongon)){
            SfaValidationUtil.validateByteMaximum(
                    tmpAllMeisaiMongon, 400, ItemName.get("riyoumeisaihyoujimongon"));
        }

        // ���p���ו\�������̑��s�����`�F�b�N
        riyouMeisaiTotalRowCnt = calcTotalRowCount(
                this.riyouMeisaiHyojiMongon, MAX_MOJISU_ONE_ROW_MEISAI_HYOJI_MONGON);

        if (MAX_ROW_RIYOU_MEISAI_HYOJI_MONGON < riyouMeisaiTotalRowCnt) {
            addFacesMessage(EmbMessageIdConstants.USE_DETAIL_OVER_TEXT);
        }

        // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND

        // �G���[������ꍇ�A���b�Z�[�W���o��
        super.handleValidatorException();

        // �쐬���ڈꗗ�̕K�{�`�F�b�N
        if (this.makingItemList.size() <= 0) {
            throw new SfaException(MessageIdConstants.VALIDATION_REQUIRED_INPUT, ItemName.get("sakuseikoumoku"));
        }
    }

    /**
     * ���ڐݒ��ʂ���J�ڎ��̃p�����[�^�`�F�b�N.<br/>
     * @param paramItem ���ڐݒ��ʂ���n���ꂽ�p�����[�^
     */
    private void verifyParametersFromItemSetting(LoanItemListModel paramItem) {
        // �G�N�Z���̃Z���w��E���ږ��E�J���������d���A�ő區�ڐ��I�[�o�[�`�F�b�N
        isPossibleAdd(paramItem);

        if (this.isAttributeChange) {

            // �T�[�r�X�����[���̒����`�F�b�N(�����ύX�{�^���������̂�)
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
     * �쐬���ڈꗗ�ɒǉ��\���𔻒f.<br/>
     * @param selectableRow �I���s
     * @throws �G�N�Z���̃Z���w��d���G���[
     * @throws ���ږ��d���G���[
     * @throws �J�������d���G���[
     * @throws �ő區�ڐ��I�[�o�[�G���[
     * @return �I���s��ʃe�[�u���֒ǉ��\��
     */
    private boolean isPossibleAdd(LoanItemListModel selectableRow) {
        for(LoanItemListModel makingRow : this.makingItemList) {
            // �����ύX�{�^��������̏ꍇ�A�{�^�������O�Ɠ������e�ł�OK
            if (!StringUtils.equals(this.attributeChangeItemName, makingRow.getItemName())) {
                // �G�N�Z���̃Z���w��d���`�F�b�N
                if (selectableRow.getCellPosition().equals(makingRow.getCellPosition())) {
                    throw new SfaException(
                            EmbMessageIdConstants.WARNING_LOAN_OVERLAPS_LIST_ITEM, ItemName.get("excelnocellshitei"));
                }
                // ���ږ��̏d���`�F�b�N
                if (selectableRow.getItemName().equals(makingRow.getItemName())) {
                    throw new SfaException(
                            EmbMessageIdConstants.WARNING_LOAN_OVERLAPS_LIST_ITEM, ItemName.get("koumokumei"));
                }
                // �J�������̏d���`�F�b�N
                if (selectableRow.getColumnName().equals(makingRow.getColumnName())) {
                    throw new SfaException(
                            EmbMessageIdConstants.WARNING_LOAN_OVERLAPS_LIST_ITEM, ItemName.get("columnmei"));
                }
            }
        }

        // �ő區�ڐ��I�[�o�[�`�F�b�N(�����ύX�{�^��������ȊO�̂Ƃ�)
        if (!isAttributeChange
                && makingItemList.size() == LoanFinancingPlanningConstants.MAX_LOAN_ITEM) {
            throw new SfaException(EmbMessageIdConstants.WARNING_LOAN_OVER_MAX);
        }
        return true;
    }

    /**
     * �쐬���ڈꗗ�ɒǉ�����Ƃ��c�ƓX���X�g��ݒ�.<br/>
     * @param selectableRow �쐬�\���ڈꗗ�̈�s
     */
    private void setSelectShopList(LoanItemListModel listItem) {
        // �������c�ƓX�I���^�̂Ƃ�
        if (listItem.getAttributeId().equals(LoanFinancingPlanningConstants.ATTRIBUTE_SELECT_SHOP)) {
            listItem.setLoanSelectionV(this.loanInfo.getLinkItemV());
        }
    }

    /**
     * �쐬���ڈꗗ����폜�\���𔻒f.<br/>
     * @param makingRow �I���s��ID
     * @throws �폜�s�\�G���[(�Œ荀��)
     * @throws �폜�s�\�G���[(�T�[�r�X��)
     * @return �쐬���ڈꗗ����폜�\��
     */
    private boolean isPossibleDelete(LoanItemListModel makingRow) {
        // �Œ荀�ڃ`�F�b�N
        if (this.isLoan
                && LoanFinancingPlanningConstants.FIX_ITEM.equals(makingRow.getFixFlag())) {
            throw new SfaException(EmbMessageIdConstants.WARNING_LOAN_DELETE_IMPOSSIBLE);
        }
        // �݌v�󋵂��T�[�r�X���ŁA���o�^�ς݂̍��ڂ͍폜�s��)
        if (isServing() && !isItemAdded(makingRow.getItemName())) {
            throw new SfaException(EmbMessageIdConstants.WARNING_LOAN_DELETE_SERVING);
        }
        return true;
    }

    /**
     * ���̓`�F�b�N(�I���\���ڃR���{).<br/>
     */
    private void verifyRequiredSeletableService(String selectedServiceTableName) {
        // �I���\���ڃR���{�̓��̓`�F�b�N
        SfaValidationUtil.validateRequiredSelect(selectedServiceTableName, ItemName.get("sentakukanoukoumoku"));

        // �G���[������ꍇ�A���b�Z�[�W���o��
        super.handleValidatorException();
    }

    /**
     * �����_��t�������l��ԋp.<br/>
     * @param intValue �������̒l
     * @param decimalValue �������̒l
     * @return �����_��t�������l
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
     * ���X�g���̍s�̈ʒu�����ւ�.<br/>
     * @param forwardIndex �O���C���f�b�N�X
     * @param followingIndex ����C���f�b�N�X
     */
    private void replacePosition(int forwardIndex, int followingIndex) {
        LoanItemListModel tempSelectItemList1 = this.makingItemList.get(forwardIndex);
        LoanItemListModel tempSelectItemList2 = this.makingItemList.get(followingIndex);

        this.makingItemList.set(forwardIndex, tempSelectItemList2);
        this.makingItemList.set(followingIndex, tempSelectItemList1);
    }

    /**
     * ���[���Œ荀�ڃ��f������쐬���ڃ��X�g��ݒ�.<br/>
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
     * �o�^����l��ݒ�.<br/>
     * �쐬���ڃ��X�g���烍�[�����ڐݒ�.<br/>
     * @param itemList ���[������
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
     * �Ώۍ��ڂ��A�ǉ����ꂽ���ڂ��o�^�ς݂̍��ڂ���ԋp.<br/>
     * @param targetItemName �`�F�b�N�Ώۍ��ڂ̍��ږ�
     * @return �ǉ����ꂽ���ڂ��o�^�ς݂̍��ڂ�
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
     * �����ύX�{�^���������A���ڐݒ��ʂɂēo�^���ꂽ���ڂ��Z�b�g.<br/>
     * @param loanItemModel ���ڐݒ��ʂ���n���ꂽ�p�����[�^(����)
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
                // NSD ADD START 2013/09/30 ID�����O�X���Z�b�g K.Takahashi
                itemRow.setIdLength(loanItemModel.getIdLength());
                // NSD ADD END   2013/09/30
                LoanFinancingUtils.getKoumokuSekkeiJyoukyou(itemRow, itemRow.getDefaultDivision()
                        , itemRow.getListSettingDivision(), itemRow.getLinkDivision());
                break;
            }
        }
    }

// 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART
    /**
     * �\����ؑ֏���(���p���׏o�̓��W�I�{�^���̔񓯊�����)
     * @param selectedCode �I�𒆂̗��p���׏o�̓��W�I�{�^���̃R�[�h�l
     * @return �񓯊��ŏ���������G���A��HTML
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
     * ���p���ו\�������C��t�[�t�b�^�[�\�������ɂ��āA�c�a�Ɋi�[���鎞�̍��v�s�������߂�D<br/>
     * @param mongon ���p���ו\�������C�܂��͎�t�[�t�b�^�[�\������
     * @param oneRowMaxMojisu �P�s�̍ő啶����
     * @return ���p���ו\�������C��t�[�t�b�^�[�\���������c�a�Ɋi�[���鎞�̍��v�s��
     */
    private static int calcTotalRowCount(String mongon, int oneRowMaxMojisu) {
        //���d�l�����i2010/03/24���_�j��
        //���p���ו\�������C��t�[�t�b�^�[�\�������́A������P�s�ɂ��A�c�a�ɂP���R�[�h�쐬����B
        //���[�U���c�a�̍s�̃o�C�g���ȏ�̕��������P�s�ɓ��͂����ꍇ�A���s���ĂQ�s�Ƃ��Ĉ����B
        //
        //���Ⴆ�΁A���[�U�����p���ו\�������̂P�s�ɂU�O�������͂����ꍇ�A
        //�u�S�O�����̍s�v�Ɓu�Q�O�����̍s�v�̌v�Q�s�Ƃ��Ĉ������ƂɂȂ�B
        //���̃��\�b�h�ł́A�e�\�������ƂP�s�̍ő啶��������A�c�a�Ɋi�[���邱�ƂɂȂ鍇�v�s�������߂�B
        //
        //���p���ו\�������̂P�s�̍ő啶�����F�S�p�S�O����
        //��t�[�t�b�^�[�\�������̂P�s�̍ő啶�����F�S�p�V�T����


        //�c�a�Ɋi�[���邱�ƂɂȂ鍇�v�s��
        int totalRowCnt = 0;

        //�P�s���Ƃ̕\������
        String[] mongonRows =  mongon.split(KAIGYO);

        for (String rowStr : mongonRows) {
            if ("".equals(rowStr)) {
                //�s���󔒁i""�j�������ꍇ�A������P�s�Ƃ݂Ȃ�
                totalRowCnt++;
            } else {
                //�s�����v�Z����
                totalRowCnt += (int) Math.ceil((double) rowStr.length() / (double) oneRowMaxMojisu);
            }
        }

        return totalRowCnt;
    }

    /**
    * ���p���ו\�������C��t�[�t�b�^�[�\�������ɂ��āA�c�a�Ɋi�[���鎞���߂̃��f�����쐬����D<br/>
    * @param vec ���f���i�[�pVector
    * @param loanTableName ���f���Ɋi�[���郍�[���e�[�u����
    * @param mongon ���p���ו\�������C�܂��͎�t�[�t�b�^�[�\������
    * @param oneRowMaxMojisu �P�s�̍ő啶����
    */
   private static void createLoanShowTextModel(
           Vector<LoanShowTextModel> vec
           , String loanTableName
           , String mongon
           , int oneRowMaxMojisu) {

       //��ʓ��͂��ꂽ�P�s���Ƃ̕\������
       String[] mongonRows =  mongon.split(KAIGYO);

       //�c�a�Ɋi�[����P�s�̕�����
       String oneRowDbStr;
       //�V�[�P���X�ԍ��i�P����n�܂�j
       int seqNo = 1;

       //�P�s�̕��������؂�isubstring����j��
       int substrCnt = 0;
       //�P�s�̕��������؂�J�n�E�I���ʒu
       int substrStartPos = 0;
       int substrEndPos = 0;

       for (String mongonRowStr : mongonRows) {

           if ("".equals(mongonRowStr)) {
               //��ʓ��͂��ꂽ�P�s�̕\���������󔒕����ł���ꍇ�A���f�����쐬

               LoanShowTextModel model = new LoanShowTextModel();
               //���[���e�[�u����
               model.setFieldLoanTableName(loanTableName);
               //�V�[�P���X�ԍ�
               model.setFieldSEQ(String.valueOf(seqNo++));
               //�P�s���̕����i���̏ꍇ�͋󔒂ƂȂ�j
               model.setFieldText(mongonRowStr);

               vec.add(model);
               continue;
           }

           //��ʓ��͂��ꂽ�P�s�̕\���������󔒈ȊO�i�������O�ȊO�j�ł���ꍇ�A
           //�\���������P�s�̍ő啶�����ŋ�؂��ă��f�����쐬����
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
               //���[���e�[�u����
               model.setFieldLoanTableName(loanTableName);
               //�V�[�P���X�ԍ�
               model.setFieldSEQ(String.valueOf(seqNo++));
               //�P�s���̕���
               model.setFieldText(oneRowDbStr);

               vec.add(model);
           }
       }
   }
// 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND
    /**
     * Id(�T�[�r�X���)�擾.<br />
     * @return Id(�T�[�r�X���)
     */
    public String getId() {
        return id;
    }

    /**
     * Id(�T�[�r�X���)�ݒ�.<br />
     * @param id Id(�T�[�r�X���)
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * ����(���[���e�[�u����)�擾.<br />
     * @return ����(���[���e�[�u����)
     */
    public String getName() {
        return name;
    }

    /**
     * ����(���[���e�[�u����)�ݒ�.<br />
     * @param name ����(���[���e�[�u����)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * ����(���[���e�[�u������)�擾.<br />
     * @return ����(���[���e�[�u������)
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * ����(���[���e�[�u������)�ݒ�.<br />
     * @param abbreviation ����(���[���e�[�u������)
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * �I���{�^�������t���O�擾.<br />
     * @return �I���{�^�������t���O
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * �I���{�^�������t���O�ݒ�.<br />
     * @param isSelected �I���{�^�������t���O
     */
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * �݌v�T�[�r�X��ގ擾.<br />
     * @return �݌v�T�[�r�X���
     */
    public String getDesignServiceKind() {
        return designServiceKind;
    }

    /**
     * �݌v�T�[�r�X��ސݒ�.<br />
     * @param designServiceKind �݌v�T�[�r�X���
     */
    public void setDesignServiceKind(String designServiceKind) {
        this.designServiceKind = designServiceKind;
    }

    /**
     * �݌v�T�[�r�X��ރ��W�I�l(���[����t)�擾.<br />
     * @return �݌v�T�[�r�X��ރ��W�I�l(���[����t)
     */
    public String getLoanuketsuke() {
        return LoanFinancingPlanningConstants.SERVICE_LOAN;
    }
    /**
     * �݌v�T�[�r�X��ރ��W�I�l(���T�[�r�X)�擾.<br />
     * @return �݌v�T�[�r�X��ރ��W�I�l(���T�[�r�X)
     */
    public String getHokaservice() {
        return LoanFinancingPlanningConstants.SERVICE_OTHERS;
    }

    /**
     * �݌v�󋵎擾.<br />
     * @return �݌v��
     */
    public String getSekkeiJyoukyou() {
        return designSituation;
    }

    /**
     * �݌v�󋵐ݒ�.<br />
     * @param situation �݌v��
     */
    public void setSekkeiJyoukyou(String situation) {
        this.designSituation = situation;
    }

    /**
     * �����I���擾.<br />
     * @return �����I��
     */
    public String getInterestRateSelectRadioValue() {
        return interestRateSelectRadioValue;
    }

    /**
     * �����I��ݒ�.<br />
     * @param interestRateSelectRadioValue �����I��
     */
    public void setInterestRateSelectRadioValue(String interestRateSelectRadioValue) {
        this.interestRateSelectRadioValue = interestRateSelectRadioValue;
    }

    /**
     * �����I�����W�I�l(����)�擾.<br />
     * @return �����I�����W�I�l(����)
     */
    public String getInterestRateValue() {
        return LoanFinancingPlanningConstants.INTEREST_RATE;
    }

    /**
     * �����I�����W�I�l(�N�ԕԍϊz)�擾.<br />
     * @return �����I�����W�I�l(�N�ԕԍϊz)
     */
    public String getYearRepaymentValue() {
        return LoanFinancingPlanningConstants.YEAR_REPAMENT;
    }

    /**
     * �쐬���ڃ��X�g�擾.<br />
     * @return �쐬���ڃ��X�g
     */
    public List<LoanItemListModel> getMakingItemList() {
        return makingItemList;
    }

    /**
     * �쐬���ڃ��X�g�ݒ�.<br />
     * @param makingItemList �쐬���ڃ��X�g
     */
    public void setMakingItemList(List<LoanItemListModel> makingItemList) {
        this.makingItemList = makingItemList;
    }

    /**
     * �I���\���ڃ��X�g�擾.<br />
     * @return �I���\���ڃ��X�g
     */
    public List<LoanItemListModel> getSelectableItemList() {
        return selectableItemList;
    }

    /**
     * �I���\���ڃ��X�g�ݒ�.<br />
     * @param selectableItemList �I���\���ڃ��X�g
     */
    public void setSelectableItemList(List<LoanItemListModel> selectableItemList) {
        this.selectableItemList = selectableItemList;
    }

    /**
     * �I���\���ڃR���{�p���X�g�擾.<br />
     * @return �I���\���ڃR���{�p���X�g
     */
    public List<SelectItem> getSelectableServiceList() {
        return selectableServiceList;
    }

    /**
     * �I���\���ڃR���{�p���X�g�ݒ�.<br />
     * @param selectableServiceList �I���\���ڃR���{�p���X�g
     */
    public void setSelectableServiceList(List<SelectItem> selectableServiceList) {
        this.selectableServiceList = selectableServiceList;
    }

    /**
     * Book�R���{�p���X�g�擾.<br />
     * @return Book�R���{�p���X�g
     */
    public List<SelectItem> getSelectBooKList() {
        return selectBooKList;
    }

    /**
     * Book�R���{�p���X�g�ݒ�.<br />
     * @param selectBooKList Book�R���{�p���X�g
     */
    public void setSelectBooKList(List<SelectItem> selectBooKList) {
        this.selectBooKList = selectBooKList;
    }

    /**
     * �\���ݎ��̕ۏ؉�ЃR���{�p���X�g�擾.<br />
     * @return �\���ݎ��̕ۏ؉�ЃR���{�p���X�g
     */
    public List<SelectItem> getSelectGuarantyCompanyList() {
        return selectGuarantyCompanyList;
    }

    /**
     * �\���ݎ��̕ۏ؉�ЃR���{�p���X�g�ݒ�.<br />
     * @param selectGuarantyCompanyList �\���ݎ��̕ۏ؉�ЃR���{�p���X�g
     */
    public void setSelectGuarantyCompanyList(List<SelectItem> selectGuarantyCompanyList) {
        this.selectGuarantyCompanyList = selectGuarantyCompanyList;
    }

    /**
     * Sheet�R���{�p���X�g�擾.<br />
     * @return Sheet�R���{�p���X�g
     */
    public List<SelectItem> getSelectSheetList() {
        return selectSheetList;
    }

    /**
     * Sheet�R���{�p���X�g�ݒ�.<br />
     * @param selectSheetList Sheet�R���{�p���X�g
     */
    public void setSelectSheetList(List<SelectItem> selectSheetList) {
        this.selectSheetList = selectSheetList;
    }

    /**
     * Book�R���{�I��l�擾.<br />
     * @return Book�R���{�I��l
     */
    public String getBook() {
        return book;
    }

    /**
     * Book�R���{�I��l�ݒ�.<br />
     * @param book Book�R���{�I��l
     */
    public void setBook(String book) {
        this.book = book;
    }

    /**
     * �\���ݎ��̕ۏ؉�ЃR���{�I��l�擾.<br />
     * @return �\���ݎ��̕ۏ؉�ЃR���{�I��l
     */
    public String getGuarantyCompany() {
        return guarantyCompany;
    }

    /**
     * �\���ݎ��̕ۏ؉�ЃR���{�I��l�ݒ�.<br />
     * @param guarantyCompany �\���ݎ��̕ۏ؉�ЃR���{�I��l
     */
    public void setGuarantyCompany(String guarantyCompany) {
        this.guarantyCompany = guarantyCompany;
    }

    /**
     * Sheet�R���{�I��l�擾.<br />
     * @return Sheet�R���{�I��l
     */
    public String getSheet() {
        return sheet;
    }

    /**
     * Sheet�R���{�I��l�ݒ�.<br />
     * @param sheet Sheet�R���{�I��l
     */
    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    /**
     * �I���\���ڃR���{�I��l�擾.<br />
     * @return �I���\���ڃR���{�I��l
     */
    public String getSelectedService() {
        return selectedService;
    }

    /**
     * �I���\���ڃR���{�I��l�ݒ�.<br />
     * @param selectedService �I���\���ڃR���{�I��l
     */
    public void setSelectedService(String selectedService) {
        this.selectedService = selectedService;
    }

    /**
     * �����i�������j�擾.<br />
     * @return �����i�������j
     */
    public String getInterestRateDecimal() {
        return interestRateDecimal;
    }

    /**
     * �����i�������j�ݒ�.<br />
     * @param interestRateDecimal �����i�������j
     */
    public void setInterestRateDecimal(String interestRateDecimal) {
        this.interestRateDecimal = interestRateDecimal;
    }

    /**
     * �����i�������j�ݒ�.<br />
     * @param interestRateInt �����i�������j
     */
    public void setInterestRateInt(String interestRateInt) {
        this.interestRateInt = interestRateInt;
    }

    /**
     * �����i�������j�擾.<br />
     * @return �����i�������j
     */
    public String getInterestRateInt() {
        return interestRateInt;
    }

    /**
     * �N�ԕԍϊz�擾.<br />
     * @return �N�ԕԍϊz
     */
    public String getYearRepayment() {
        return yearRepayment;
    }

    /**
     * �N�ԕԍϊz�ݒ�.<br />
     * @param yearRepayment �N�ԕԍϊz
     */
    public void setYearRepayment(String yearRepayment) {
        this.yearRepayment = yearRepayment;
    }

    /**
     * �݌v��(�V�K)�t���O�擾.<br />
     * @return �݌v��(�V�K)�t���O
     */
    public boolean isNew() {
        return this.designSituation.equals(LoanFinancingPlanningConstants.DesignSituation.NEW.code());
    }

    /**
     * �݌v��(�݌v��)�t���O�擾.<br />
     * @return �݌v��(�݌v��)�t���O
     */
    public boolean isDesigning() {
        return this.designSituation.equals(LoanFinancingPlanningConstants.DesignSituation.DESIGNING.code());
    }

    /**
     * �݌v��(�T�[�r�X��)�t���O�擾.<br />
     * @return �݌v��(�T�[�r�X��)�t���O
     */
    public boolean isServing() {
        return this.designSituation.equals(LoanFinancingPlanningConstants.DesignSituation.SERVING.code());
    }

    /**
     * �݌v��(�T�[�r�X�I��)�t���O�擾.<br />
     * @return �݌v��(�T�[�r�X�I��)�t���O
     */
    public boolean isServiceEnd() {
        return this.designSituation.equals(LoanFinancingPlanningConstants.DesignSituation.SERVICE_END.code());
    }

    /**
     * �݌v�T�[�r�X��ރ��W�I�Ń��[����I�����Ă��邩�擾.<br />
     * @return �݌v�T�[�r�X��ރ��W�I�Ń��[����I�����Ă��邩
     */
    public boolean isKinri() {
        return isKinri;
    }

    /**
     * �����I����ރ��W�I�ŋ�����I�����Ă��邩�擾.<br />
     * @return �����I����ރ��W�I�ŋ�����I�����Ă��邩
     */
    public boolean isLoan() {
        return isLoan;
    }

    /**
     * �o�^�ς݂��擾.<br />
     * @return �o�^�ς݂�
     */
    public boolean isRegisted() {
        return isRegisted;
    }
    // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jSTART
    /**
     * ���p���׏o�̓��W�I�Ŗ�����I�����Ă��邩�擾.<br />
     * @return ���p���׏o�̓��W�I�Ŗ�����I�����Ă��邩
     */
    public boolean isRiyouMeisai() {
        return isRiyouMeisaiOut;
    }

    /**
     * ���p���׏o��(�L��or����)���擾���܂��B
     * @return ���p���׏o��(�L��or����)
     */
    public String getRiyouMeisaiOut() {
        return riyouMeisaiOut;
    }

    /**
     * ���p���׏o��(�L��or����)��ݒ肵�܂��B
     * @param riyouMeisaiOut ���p���׏o��(�L��or����)
     */
    public void setRiyouMeisaiOut(String riyouMeisaiOut) {
        this.riyouMeisaiOut = riyouMeisaiOut;
    }

    /**
     * �ӎv�m�F(�L��or����)���擾���܂��B
     * @return �ӎv�m�F(�L��or����)
     */
    public String getIsiKakunin() {
        return isiKakunin;
    }

    /**
     * �ӎv�m�F(�L��or����)��ݒ肵�܂��B
     * @param isiKakunin �ӎv�m�F(�L��or����)
     */
    public void setIsiKakunin(String isiKakunin) {
        this.isiKakunin = isiKakunin;
    }

    /**
     * ���p���ו\���������擾���܂��B
     * @return ���p���ו\������
     */
    public String getRiyouMeisaiHyojiMongon() {
        return riyouMeisaiHyojiMongon;
    }

    /**
     * ���p���ו\��������ݒ肵�܂��B
     * @param riyouMeisaiHyojiMongon ���p���ו\������
     */
    public void setRiyouMeisaiHyojiMongon(String riyouMeisaiHyojiMongon) {
        this.riyouMeisaiHyojiMongon = riyouMeisaiHyojiMongon;
    }

    /**
     * ���p���׏o�̓��W�I(�L��)�擾.<br />
     * @return ���p���׏o�̓��W�I�l(�L��)
     */
    public String getRiyouMeisaiOutAri() {
        return LoanFinancingPlanningConstants.RIYOUMEISAI_OUT_ARI;
    }

    /**
     * ���p���׏o�̓��W�I(����)�擾.<br />
     * @return ���p���׏o�̓��W�I�l(����)
     */
    public String getRiyouMeisaiOutNasi() {
        return LoanFinancingPlanningConstants.RIYOUMEISAI_OUT_NASI;
    }

    /**
     * ��t�敪���W�I(���)�擾.<br />
     * @return  ��t�敪���W�I�l(���)
     */
    public String getUketukeKbnKaiin() {
        return LoanFinancingPlanningConstants.UKETUKE_KBN_KAIIN;
    }

    /**
     * ��t�敪���W�I(����)�擾.<br />
     * @return  ��t�敪���W�I�l(����)
     */
    public String getUketukeKbnHiKaiin() {
        return LoanFinancingPlanningConstants.UKETUKE_KBN_HIKAIIN;
    }

    /**
     * ��t�敪���W�I(����)�擾.<br />
     * @return  ��t�敪���W�I�l(����)
     */
    public String getUketukeKbnBoth() {
        return LoanFinancingPlanningConstants.UKETUKE_KBN_BOTH;
    }

    /**
     * �ӎv�m�F���W�I(�L��)�擾.<br />
     * @return  �ӎv�m�F���W�I�l(�L��)
     */
    public String getIsiKakuninAri() {
        return LoanFinancingPlanningConstants.ISI_KAKUNIN_ARI;
    }

    /**
     * �ӎv�m�F���W�I(�L��)�擾.<br />
     * @return  �ӎv�m�F���W�I�l(�L��)
     */
    public String getIsiKakuninNasi() {
        return LoanFinancingPlanningConstants.ISI_KAKUNIN_NASI;
    }
    // 2013/11/19 �X��s����捞 ���[������Ή��i�ӎv�m�F�@�\�jEND
}