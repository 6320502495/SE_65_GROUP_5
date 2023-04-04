<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('agreeing', function (Blueprint $table) {
            $table->id();
            $table->string('campaign_id');
            $table->index('campaign_id');
            $table->foreignID('campaign_id')->references('ID')->on('campaign')->onDelete('cascade');
            $table->string('account_id');
            $table->index('account_id');
            $table->foreignID('account_id')->references('ID')->on('account')->onDelete('cascade');
            $table->integer('total_donation')->nullable();
            $table->enum('status', ['None', 'Agree', 'Reject'])->default('None');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('users');
    }
};
