package com.example.blogapp.di

import com.example.blogapp.core.data.repository.CommentRepositoryImpl
import com.example.blogapp.core.data.repository.PostRepositoryImpl
import com.example.blogapp.core.data.repository.UserRepositoryImpl
import com.example.blogapp.core.domain.repository.CommentRepository
import com.example.blogapp.core.domain.repository.PostRepository
import com.example.blogapp.core.domain.repository.UserRepository
import com.example.blogapp.core.domain.use_cases.global.GlobalUseCases
import com.example.blogapp.core.domain.use_cases.global.TakeAllLikedPosts
import com.example.blogapp.core.domain.use_cases.global.TakeAllTagsUseCase
import com.example.blogapp.core.domain.use_cases.validation.ValidateContent
import com.example.blogapp.core.domain.use_cases.validation.ValidateData
import com.example.blogapp.core.domain.use_cases.validation.ValidateLink
import com.example.blogapp.core.domain.use_cases.validation.ValidatePhoneNumber
import com.example.blogapp.core.domain.use_cases.validation.ValidationPicture
import com.example.blogapp.feature_blog.domain.use_cases.CreatePostUseCase
import com.example.blogapp.feature_blog.domain.use_cases.CreatingCommentUseCase
import com.example.blogapp.feature_blog.domain.use_cases.DeletePostUseCase
import com.example.blogapp.feature_blog.domain.use_cases.DeletingCommentUseCase
import com.example.blogapp.feature_blog.domain.use_cases.DislikePostUseCase
import com.example.blogapp.feature_blog.domain.use_cases.LikePostUseCase
import com.example.blogapp.feature_blog.domain.use_cases.PostUseCases
import com.example.blogapp.feature_blog.domain.use_cases.TakeCommentsUseCase
import com.example.blogapp.feature_blog.domain.use_cases.TakePostUseCase
import com.example.blogapp.feature_blog.domain.use_cases.TakePostsUseCase
import com.example.blogapp.feature_blog.domain.use_cases.TakeUserDataUseCase
import com.example.blogapp.feature_blog.domain.use_cases.TakeUserPostsUseCase
import com.example.blogapp.feature_blog.domain.use_cases.TakeUsersUseCase
import com.example.blogapp.feature_login_register.domain.use_cases.CreateUserUseCase
import com.example.blogapp.feature_login_register.domain.use_cases.ResetPasswordUseCase
import com.example.blogapp.feature_login_register.domain.use_cases.SignInUseCase
import com.example.blogapp.feature_login_register.domain.use_cases.SignInUseCases
import com.example.blogapp.feature_profile.domain.use_cases.ProfileUseCases
import com.example.blogapp.feature_profile.domain.use_cases.SetUpNewEmailUseCase
import com.example.blogapp.feature_profile.domain.use_cases.SetUpNewPassword
import com.example.blogapp.feature_profile.domain.use_cases.SignOutUseCase
import com.example.blogapp.feature_profile.domain.use_cases.UpdateProfileUseCase
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateEmail
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidatePassword
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateRePassword
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateTerms
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideValidateUseCases(): ValidateUseCases {
        return ValidateUseCases(
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validateRePassword = ValidateRePassword(),
            validateTerms = ValidateTerms(),
            validateData = ValidateData(),
            validatePhoneNumber = ValidatePhoneNumber(),
            validateContent = ValidateContent(),
            validateLink = ValidateLink(),
            validatePicture = ValidationPicture()
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providePostRepository(): PostRepository {
        return PostRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideCommentRepository(): CommentRepository {
        return CommentRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSignInUseCases(repository: UserRepository): SignInUseCases {
        return SignInUseCases(
            createUserUseCase = CreateUserUseCase(repository),
            signInUseCase = SignInUseCase(repository),
            resetPasswordUseCase = ResetPasswordUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: UserRepository): ProfileUseCases {
        return ProfileUseCases(
            signOutUseCase = SignOutUseCase(repository),
            setUpNewPassword = SetUpNewPassword(repository),
            setUpNewEmailUseCase = SetUpNewEmailUseCase(repository),
            updateProfileUseCase = UpdateProfileUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providePostUseCases(
        repository: UserRepository,
        postRepository: PostRepository,
        commentRepository: CommentRepository
    ): PostUseCases {
        return PostUseCases(
            takeUserDataUseCase = TakeUserDataUseCase(repository),
            createPostUseCase = CreatePostUseCase(postRepository),
            takePostsUseCase = TakePostsUseCase(postRepository),
            takeUsersUseCase = TakeUsersUseCase(repository),
            takePostUseCase = TakePostUseCase(postRepository),
            likePostUseCase = LikePostUseCase(postRepository),
            dislikePostUseCase = DislikePostUseCase(postRepository),
            deletePostUseCase = DeletePostUseCase(postRepository),
            addCommentUseCase = CreatingCommentUseCase(commentRepository),
            takeCommentsUseCase = TakeCommentsUseCase(commentRepository),
            deletingCommentUseCase = DeletingCommentUseCase(commentRepository),
            takeUserPostsUseCase = TakeUserPostsUseCase(postRepository)
        )
    }

    @Provides
    @Singleton
    fun provideGlobalUseCases(
        repository: UserRepository,
        postRepository: PostRepository
    ): GlobalUseCases {
        return GlobalUseCases(
            takeAllTagsUseCase = TakeAllTagsUseCase(repository),
            takeAllLikedPosts = TakeAllLikedPosts(postRepository)
        )
    }
}