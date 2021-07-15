package com.caffeine.capin.customview

class CapinDialogBuilder(
    private var title: String?,
) {
    private var contentDialogTitle: String? = null
    private var content: String? = null
    private var contentDialogButtons: Boolean = false
    private var buttonArray: ArrayList<CapinDialogButton>? = null
    private var exitButton: Boolean = false
    private var listener: DialogClickListener? = null

    fun build(): CapinDialog {
        return CapinDialog(
            title,
            buttonArray,
            exitButton,
            contentDialogTitle,
            content,
            contentDialogButtons,
            listener
        )
    }

    fun setButtonArray(capinButtonArray: ArrayList<CapinDialogButton>): CapinDialogBuilder {
        this.buttonArray = capinButtonArray
        return this
    }

    fun setExitButton(exitButton: Boolean): CapinDialogBuilder {
        this.exitButton = exitButton
        return this
    }

    fun setContentDialogTitile(contentDialogTitle: String): CapinDialogBuilder {
        this.contentDialogTitle = contentDialogTitle
        return this
    }

    fun setContent(content: String): CapinDialogBuilder {
        this.content = content
        return this
    }

    fun setContentDialogButtons(contentDialogButtons: Boolean, listener: DialogClickListener): CapinDialogBuilder {
        this.contentDialogButtons = contentDialogButtons
        this.listener = listener
        return this
    }
}