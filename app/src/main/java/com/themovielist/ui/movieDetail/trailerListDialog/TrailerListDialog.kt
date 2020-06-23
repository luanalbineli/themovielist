package com.themovielist.ui.movieDetail.trailerListDialog

class MovieTrailerListDialog : BaseFullscreenDialogWithList<MovieTrailerModel, MovieTrailerListDialogContract.View>(), MovieTrailerListDialogContract.View {
    private val mMovieReviewAdapter by lazy { MovieTrailerAdapter() }

    private val mLinearLayoutManager by lazy { LinearLayoutManager(rvFullscreenFragmentDialog.context, LinearLayoutManager.VERTICAL, false) }

    @Inject
    lateinit var mPresenter: MovieTrailerListDialogPresenter

    override val presenterImplementation: BasePresenter<MovieTrailerListDialogContract.View>
        get() = mPresenter

    override val viewImplementation: MovieTrailerListDialogContract.View
        get() = this

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMovieReviewAdapter.setOnItemClickListener { _, item -> YouTubeUtil.openYouTubeVideo(activity, item.source) }

        val dividerItemDecoration = DividerItemDecoration(rvFullscreenFragmentDialog.context, mLinearLayoutManager.orientation)

        rvFullscreenFragmentDialog.addItemDecoration(dividerItemDecoration)
        rvFullscreenFragmentDialog.layoutManager = mLinearLayoutManager
        rvFullscreenFragmentDialog.adapter = mMovieReviewAdapter

        mPresenter.start(mList)

        setTitle(R.string.all_trailers)
    }

    override fun showTrailersIntoList(movieReviewList: List<MovieTrailerModel>) {
        mMovieReviewAdapter.addItems(movieReviewList)
    }

    override fun onInjectDependencies(applicationComponent: ApplicationComponent) {
        DaggerFragmentComponent.builder()
            .applicationComponent(PopularMovieApplication.getApplicationComponent(activity))
            .build()
            .inject(this)
    }

    companion object {

        fun getInstance(movieModelList: List<MovieTrailerModel>): MovieTrailerListDialog {
            return BaseFullscreenDialogWithList.createNewInstance(MovieTrailerListDialog::class.java, movieModelList)
        }
    }
}
